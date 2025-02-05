package coroutines

import DSL.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlin.random.Random

//fun main(): Unit = runBlocking {
//    launch {
//        launch {
//            delay(1000)
//            throw Error("Some error")
//        }
//    }
//    launch {
//        delay(2000)
//        println("Will not be printed")
//    }
//    launch {
//        delay(500) // faster than the exception
//        println("Will be printed")
//    }
//    launch {
//        delay(2000)
//        println("Will not be printed")
//    }
//}

//fun main(): Unit = runBlocking {
//// DON'T DO THAT!
//    launch(context = SupervisorJob()) { // 1
//        launch {
//            delay(1000)
//            throw Error("Some error")
//        }
//        launch {
//            delay(2000)
//            println("Will not be printed")
//        }
//    }
//    delay(3000)
//    launch {
//        println("this is a message outside the launch")
//    }
//}
//
//fun main(): Unit = runBlocking {
//// DON'T DO THAT!
//    supervisorScope {
//        launch {
//            delay(1000)
//            throw Error("Some error")
//        }
//        launch {
//            delay(2000)
//            println("Will be printed")
//        }
//        launch {
//            delay(2000)
//            println("Will be printed")
//        }
//    }
//    delay(500)
//    println("Done")
//}

class MyException : Throwable()

//suspend fun main() = supervisorScope {
//    val str1 = async<String> {
//        throw MyException()
//    }
//    val str2 = async {
//        delay(2000)
//        "Text2"
//    }
//    try {
//        delay(3000)
//        println(str1.await())
//    } catch (e: MyException) {
//        println(e)
//    }
//    println(str2.await())
//}

fun Flow<*>.counter(): Flow<Int> {
    var counter = 0
    return this.map {
        counter++
// to make it busy for a while
        List(100) { Random.nextLong() }.shuffled().sorted()
        counter
    }
}
//fun main(): Unit = runBlocking {
//    val value = launch {
//        try {
//            throw RuntimeException("Exception in launch")
//        } catch (e: Exception) {
//            println("Caught exception in launch") // Will not print!
//        }
//    }
//    println(value)
//}


data class UserDetails(val userData: UserData, val userPreference: UserPreference?)

data class UserData(val name: String, val age: String)

data class UserPreference(val something: Boolean)

class UserRepository {
    private var users: List<User> = listOf()

    private val lock = Any()
    fun loadAll(): List<User> = users

    fun add(user: User) = synchronized(lock) {
        users = users + user
    }
}


suspend fun main() = supervisorScope {
    var value = 0
    val str1 = async<String> {
        println("this is a message")
        value = 1000
        throw MyException()
    }
    val str2 = async {
        delay(2000)
        "Text2"
    }
    try {
        delay(2000)
        println(str1.await())
    } catch (e: MyException) {
        println(e)
    }
    println(str2.await())
}

suspend fun fetchUserDetails2(): UserDetails? = try {
    supervisorScope {
        val userData = async { fetchUserData() }
        val userPreferences = async { fetchUserPreferences() }
        UserDetails(userData.await(), userPreferences.await())
    }
} catch (e: Throwable) {
    println("Error in fetchUserDetails: $e")
    null
}

fun fetchUserData() = UserData("ajesh", " 33")

fun fetchUserPreferences(): Nothing = throw error("some error")


