package coroutines.dispatcher

import kotlinx.coroutines.*

class DiscNewsRepository(
    private val discReader: DiscReader
) : NewsRepository {
    override suspend fun getNews(newsId: String): News {
        val news = withContext(Dispatchers.IO.limitedParallelism(200)) {
            val (title, content) = discReader.read("user/$newsId")
            return@withContext News(title, content)
        }
        return news
    }
}

interface DiscReader {
    fun read(key: String): List<String>
}

interface NewsRepository {
    suspend fun getNews(newsId: String): News
}

data class News(val title: String, val content: String)

