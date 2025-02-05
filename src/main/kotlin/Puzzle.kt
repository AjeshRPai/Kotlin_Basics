import coroutines.fetchUserData
import kotlinx.coroutines.*

suspend fun doLongRunningTaskOne(): Int {
    delay(2400)
    println("Task one is getting executed")
    return 33
}

suspend fun doLongRunningTaskTwo(): Int {
    delay(1500)
    println("Task two is getting executed")
    return 6
}


suspend fun main(): Unit = coroutineScope {

}

suspend fun example1() {
    val value = GlobalScope.async {
        delay(2000L)
        1
    }
    println("Calculating")
    print(value.await())
    print(value.await())
    print(value.await())
}
