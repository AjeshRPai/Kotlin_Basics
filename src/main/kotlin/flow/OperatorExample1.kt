package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// Data Models
data class Product(val id: Int, val name: String, val description: String, val pageId: Int)
data class Bookmark(val productId: Int, val pageId: Int)
data class UiModel(val id: Int, val name: String, val description: String, val isBookmarked: Boolean)

// Function to emit products
fun getProductsFlow(pageId: Int): Flow<List<Product>> = flow {
    emit(
        listOf(
            Product(1, "Product A", "Description A", pageId),
            Product(2, "Product B", "Description B", pageId),
            Product(3, "Product C", "Description C", pageId)
        )
    )
}

fun getBookmarksFlow(pageId: Int): Flow<List<Bookmark>> = flow {
    delay(5000)
    if (pageId == 1) {
        emit(listOf(Bookmark(1, pageId), Bookmark(3, pageId)))
    } else {
        throw Exception("Failed to fetch bookmarks")
    }
}


// ViewModel class
class ViewModel {

    // Case 1: Wait for both flows to emit before combining
    fun getUiModelFlowWithWait(pageId: Int): Flow<List<UiModel>> = flow {
        try {
            getProductsFlow(pageId)
                .zip(getBookmarksFlow(pageId)) { products, bookmarks ->
                    val bookmarkSet = bookmarks.map { it.productId }.toSet()

                    // Combine products with bookmark information
                    products.map { product ->
                        UiModel(
                            id = product.id,
                            name = product.name,
                            description = product.description,
                            isBookmarked = product.id in bookmarkSet
                        )
                    }
                }
                .collect { uiModels ->
                    emit(uiModels) // Emit the list of UI models
                }
        } catch (e: Exception) {
            emit(emptyList()) // Emit an empty list if bookmarks flow fails
            println("Error fetching bookmarks: ${e.message}")
        }
    }

    fun getUiModelFlowWithoutWait2(pageId: Int): Flow<UiModel> =
        merge(
            getProductsFlow(pageId).flatMapConcat { products ->
                // Emit each product as a UiModel (initially not bookmarked)
                products.asFlow().map { product ->
                    UiModel(
                        id = product.id,
                        name = product.name,
                        description = product.description,
                        isBookmarked = false
                    )
                }
            },
            getBookmarksFlow(pageId).flatMapConcat { bookmarks ->
                // Emit updates for bookmarked products
                bookmarks.asFlow().map { bookmark ->
                    UiModel(
                        id = bookmark.productId,
                        name = "", // Placeholder, won't overwrite existing name
                        description = "", // Placeholder, won't overwrite existing description
                        isBookmarked = true
                    )
                }
            }
        ).scan(emptyMap<Int, UiModel>()) { acc, uiModel ->
            // Update the map of UiModels
            val updatedUiModel = acc[uiModel.id]?.copy(
                isBookmarked = uiModel.isBookmarked || acc[uiModel.id]?.isBookmarked == true,
                name = if (uiModel.name.isNotBlank()) uiModel.name else acc[uiModel.id]?.name ?: "",
                description = if (uiModel.description.isNotBlank()) uiModel.description else acc[uiModel.id]?.description ?: ""
            ) ?: uiModel

            acc + (uiModel.id to updatedUiModel)
        }.flatMapConcat { uiModelMap ->
            // Emit the latest list of UI models
            uiModelMap.values.asFlow()
        }





    // Case 2: Combine flows as they emit
    fun getUiModelFlowWithoutWait(pageId: Int): Flow<UiModel> =
        combine(getProductsFlow(pageId), getBookmarksFlow(pageId)) { products, bookmarks ->
            val bookmarkSet = bookmarks.map { it.productId }.toSet()

            // Map each product to a UiModel, updating bookmark status
            products.map { product ->
                UiModel(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    isBookmarked = product.id in bookmarkSet
                )
            }
        }
            .flatMapConcat { uiModels ->
                // Emit each UiModel individually
                uiModels.asFlow()
            }
            .distinctUntilChanged() // Avoid emitting duplicates

}


fun main() = runBlocking {
    val viewModel = ViewModel()
    val pageId = 1

//    println("Case 1: Wait for both flows to emit before combining")
//    viewModel.getUiModelFlowWithWait(pageId).collect { uiModels ->
//        println("UI Models (Wait): $uiModels")
//    }
//
//    println("\nCase 2: Combine flows as they emit")
//    viewModel.getUiModelFlowWithoutWait(pageId).collect { uiModel ->
//        println("UI Model (Real-time): $uiModel")
//    }

    viewModel.getUiModelFlowWithoutWait2(pageId).collect { uiModel ->
        println("UI Model (Real-time): $uiModel")
    }
}

