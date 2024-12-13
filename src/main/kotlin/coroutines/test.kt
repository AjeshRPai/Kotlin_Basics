package coroutines

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        var sum = 0
        val job = launch(Dispatchers.Default) {
            for (i in 1..1000) {
                if (isActive) {
                    sum += i
                    println("Partial sum after $i iterations: $sum")
                }
            }
        }
        println("I'm tired of waiting!")
        job.cancelAndJoin()
        println("Now I can quit.")
    }
}