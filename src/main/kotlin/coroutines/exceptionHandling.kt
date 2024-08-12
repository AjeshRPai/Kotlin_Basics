package coroutines

import kotlinx.coroutines.*

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

suspend fun main(): Unit {
   val userDetails =  fetchUserDetails()
    println(userDetails)
    val userDetails2 = fetchUserDetails2()
    println(userDetails2)
}

data class UserDetails(val userData: UserData, val userPreference: UserPreference?)

data class UserData(val name:String, val age:String)

data class UserPreference (val something:Boolean)

suspend fun fetchUserDetails(): UserDetails =
    coroutineScope {
        val userData = async { fetchUserData() }
        val userPreferences = async {
            try {
                fetchUserPreferences()
            } catch (e: Throwable) {
                println("Error in fetchUserPreferences: $e")
                null
            }
        }
        UserDetails(userData.await(), userPreferences.await())
    }


suspend fun fetchUserDetails2(): UserDetails? = try {
    coroutineScope {
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


