package Generics

import kotlin.collections.mutableListOf

suspend fun main() {
    // generic container to hold all parcels
    val parcelContainer = Container<Parcel>()

    // dummy code to insert parcels
    parcelContainer.addItem(Parcel("first parcel"))
    parcelContainer.addItem(Parcel("second parcel"))

    val letterParcelContainer = Container<LetterParcel>()
    letterParcelContainer.addItem(LetterParcel("first letter"))
    letterParcelContainer.addItem(LetterParcel("second letter"))

    parcelContainer.transferParcels(letterParcelContainer)

    for (parcel in parcelContainer.getItems()) {
        println(parcel.content)
    }

    // star projection
    val loginEvent = Event(
        "User Logged In",
        mapOf(
            "username" to "johndoe",
            "loginTime" to System.currentTimeMillis()
        )
    )

    val purchaseEvent = Event(
        "Purchase Completed",
        mapOf(
            "itemName" to "Laptop",
            "price" to 1299.99,
            "quantity" to 1
        )
    )

    // Log metadata for each event
    logEventMetadata(loginEvent)
    logEventMetadata(purchaseEvent)
}

open class Parcel(open val content: String)
class LetterParcel(override val content: String): Parcel(content)

class Container<T> {
    private val items = mutableListOf<T>()

    fun addItem(item: T) {
        items.add(item)
    }

    fun getItem(): T {
        return items.removeAt(0)
    }

    fun getItems() = items

    fun transferParcels(from: Container<out T>) {
        val parcel = from.getItem()
        addItem(parcel)
    }
}



class Event(val name: String, val metadata: Map<String, *>)

fun logEventMetadata(event: Event) {
    println("Event: ${event.name}")
    for ((key, value) in event.metadata) {
        println("Metadata Key: $key, Value Type: ${value?.javaClass?.simpleName}, Value: $value")
    }
}



