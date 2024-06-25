package Coroutines

import kotlinx.coroutines.*

    suspend fun workWithActive() {
        val startTime = System.currentTimeMillis()
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) {
            yield()
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("Hello ${i++}")
                nextPrintTime += 500L
            }
        }
    }

    fun main(args: Array<String>) = runBlocking<Unit> {
        val job = launch(Dispatchers.Default) {
            try {
                workWithActive()
            } finally {
                withContext(NonCancellable) {
                    delay(1000L)
                    println("Cleanup done!")
                }
            }
        }
        delay(1000L)
        println("Cancel!")
        job.cancel()
        println("Done!")
    }
