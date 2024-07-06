package coroutines.synchronized

import kotlinx.coroutines.*

class UserDownloader(private val api: NetworkService) {

    val dispatcher = Dispatchers.IO.limitedParallelism(1)

    private var users = listOf<User>()

    fun downloaded(): List<User> = users

    suspend fun getUser(id: Int) = withContext(dispatcher) {
        val newUser = api.getUser(id)
        users += newUser
    }
}


data class User(val name: String)

interface NetworkService {
    suspend fun getUser(id: Int): User
}

class FakeNetworkService : NetworkService {
    override suspend fun getUser(id: Int): User {
        delay(2)
        return User("User$id")
    }
}

suspend fun main() = coroutineScope {
    val downloader = UserDownloader(FakeNetworkService())
    coroutineScope {
        repeat(1_000_000) {
            launch {
                downloader.getUser(it)
            }
        }
    }
    print(downloader.downloaded().size) // ~714725
}


