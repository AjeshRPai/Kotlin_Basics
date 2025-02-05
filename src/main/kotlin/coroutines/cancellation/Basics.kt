package coroutines.cancellation

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

//suspend fun main(): Unit = coroutineScope {
//    val job = launch {
//        repeat(1_000) { i ->
//            delay(200)
//            println("Printing $i")
//        }
//    }
//    delay(1100)
//    job.cancel()
//    job.join()
//    println("Cancelled successfully")
//}
// (0.2 sec)
// Printing 0
// (0.2 sec)
// Printing 1
// (0.2 sec)
// Printing 2
// (0.2 sec)
// Printing 3
// (0.2 sec)
// Printing 4
// (0.1 sec)
// Cancelled successfully


//suspend fun main(): Unit = coroutineScope {
//    val job = launch {
//        try {
//            repeat(1_000) { i ->
//                delay(200)
//                println("Printing $i")
//            }
//        } catch (e: CancellationException) {
//            println("Cancelled with $e")
//            throw e
//        } finally {
//            println("Finally")
//        }
//    }
//    delay(700)
//    job.cancel()
//    job.join()
//    println("Cancelled successfully")
//    delay(1000)
//}


// (0.2 sec)
// Printing 0
// (0.2 sec)
// Printing 1
// (0.2 sec)
// Printing 2
// (0.1 sec)
// Cancelled with JobCancellationException...
// Finally
// Cancelled successfully


// cancel using cancelChildren vs cancel

// val superVisorScope = CoroutineScope(SupervisorJob())
//
//suspend fun main() {
//    val scope = CoroutineScope(Job())
//    scope.cancel()
//    val job = scope.launch { // will be ignored
//        println("launch after cancel in scope")
//    }
//    job.join()
//
//    superVisorScope.coroutineContext.cancelChildren()
//    val job2 = superVisorScope.launch {
//        println("launch after cancel in super visor scope")
//    }
//    job2.join()
//}


// Cleaning up before finishing
//
//suspend fun main(): Unit = coroutineScope {
//    val job = Job()
//    launch(job) {
//        try {
//            println("Coroutine started")
//            delay(200)
//            println("Coroutine finished")
//        } finally {
//            println("Finally")
//            launch {
//                println("Children executed")
//            }
//            delay(1000L)
//            println("Cleanup done")
//        }
//    }
//    delay(100)
//    job.cancelAndJoin()
//    println("Done")
//}

// with Context
suspend fun main(): Unit = coroutineScope {
    launch {
        try {
            println("Coroutine started")
            delay(200)
            println("Coroutine finished")
        } finally {
            println("Finally")
            withContext(NonCancellable) {
                launch { println("Children executed") }
                delay(1000L)
                println("Cleanup done")
            }
        }
    }
    delay(100)
    println("Done")
}