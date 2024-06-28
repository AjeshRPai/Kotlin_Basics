package coroutines

import kotlinx.coroutines.*

// https://kt.academy/article/cc-cancellation


//suspend fun main(): Unit = coroutineScope {
//    val job = launch {
//        repeat(1_000) { i ->
//            delay(200)
//            println("Printing $i")
//        }
//    }
//
//    delay(1100)
//    job.cancel() // we can pass an argument to cancel to throw an exception, a subclass of CancellationException
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


// Finally block and Join
// Finally block runs after the job is cancelled
// Join waits for the job to finish, ie the job is cancelled and then the join is called

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
//    job.join() // if we dont call Join here, there would be a race condition and cancelled succesfully
//    // would be printed before the job is actually cancelled
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


// Invoke on Completion
// The invokeOnCompletion handler is called with:

// null if the job finished with no exception;
// CancellationException if the coroutine was cancelled;
// the exception that finished a coroutine (more about this in the next chapter).
// invokeOnCompletion is called synchronously during cancellation,
// and we can't control the thread in which it will be running.
// It can be further customized with the onCancelling3 and invokeImmediately4 parameters.

suspend fun main(): Unit = coroutineScope {
    val job = launch {
        repeat(1_000) { i ->
            delay(200)
            println("Printing $i")
        }
    }
    job.invokeOnCompletion {
        if (it is CancellationException) {
            println("Cancelled with $it")
        }
        println("Finally")
    }
    delay(700)
    job.cancel()
    job.join()
    println("Cancelled successfully")
    delay(1000)
}
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

