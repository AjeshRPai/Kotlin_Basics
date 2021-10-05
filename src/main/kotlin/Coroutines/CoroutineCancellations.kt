package Coroutines

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Job()+Dispatchers.IO)
    println("Coroutine start")
    val job = scope.launch {
        println("Running coroutines")
        println(this.toString())
        delay(500)
    }
    runBlocking {
        job.cancel()
        job.join()
    }

    val job1 = scope.launch {
        throwException()
    }
}

suspend fun throwException() {
    throw NullPointerException()
}