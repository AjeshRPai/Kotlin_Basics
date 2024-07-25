package coroutines.scopefunction

import kotlinx.coroutines.*

suspend fun main() {
    coroutineScope {
        launch {
            func1()
        }
        println("1")
        func2()
    }
}


suspend fun func1() {
    delay(1000L)
    println("C")
    delay(2000L)
    println("D")
}


suspend fun func2() = coroutineScope {
    delay(1000L)
    println("A")
    delay(1000L)
    println("B")
}