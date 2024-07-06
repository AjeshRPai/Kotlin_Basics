package coroutines.scope

import kotlinx.coroutines.*
import java.util.*

abstract class BasePresenter(
    private val onError: (Throwable) -> Unit = {}
) {
    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable -> onError.invoke(throwable)}

    val coroutineContext =  Dispatchers.Main + SupervisorJob() + coroutineExceptionHandler

    val scope: CoroutineScope = CoroutineScope(coroutineContext)

    fun onDestroy() {
        coroutineContext.cancelChildren()
    }
}

class MainPresenter(
    private val view: MainView,
    private val userRepo: UserRepository,
    private val newsRepo: NewsRepository
) : BasePresenter(view::onError) {

    fun onCreate() {
        scope.launch {
            val user = userRepo.getUser()
            view.showUserData(user)
        }
        scope.launch {
            val news = newsRepo.getNews()
                .sortedByDescending { it.date }
            view.showNews(news)
        }
    }
}

interface MainView {
    fun onError(throwable: Throwable): Unit
    fun showUserData(user: UserData)
    fun showNews(news: List<News>)
}

interface UserRepository {
    suspend fun getUser(): UserData
}

interface NewsRepository {
    suspend fun getNews(): List<News>
}

data class UserData(val name: String)
data class News(val date: Date)