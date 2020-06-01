package Coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

object CoroutineBuildersExample {

    @JvmStatic
    fun main(args: Array<String>) {

        //A CoroutineScope keeps track of any coroutine you create using launch or async (these are extension functions on CoroutineScope).
        // The ongoing work (running coroutines) can be canceled by calling scope.cancel() at any point in time.
        //
        //You should create a CoroutineScope whenever you want to start and control the lifecycle of coroutines
        // in a particular layer of your app.

        val scope = CoroutineScope(Job() + Dispatchers.Main)


        //A Job is a handle to a coroutine.
        // For every coroutine that you create (by launch or async),
        // it returns a Job instance that uniquely identifies the coroutine and manages its lifecycle.


        /*
        CoroutineContext
        The CoroutineContext is a set of elements that define the behavior of a coroutine.
        It’s made of:
        Job — controls the lifecycle of the coroutine.
        CoroutineDispatcher — dispatches work to the appropriate thread.
        CoroutineName — name of the coroutine, useful for debugging.
        CoroutineExceptionHandler — handles uncaught exceptions, will be covered in Part 3 of the series.

        **/

//        val job = scope.launch(Dispatchers.IO) {
//            // new coroutine
//            launchCoroutines()
//        }

        //testChildScope()
        parentChildRelation()
    }

    fun testChildScope() = runBlocking<Unit> {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            // it spawns two other jobs, one with GlobalScope
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            // and the other inherits the parent context
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        println("Cancelling the coroutines using Request job")
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        println("main: Who has survived request cancellation?")
    }

    fun parentChildRelation() = runBlocking<Unit> {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            repeat(3) { i -> // launch a few children jobs
                launch  {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                    println("Coroutine $i is done")
                }
            }
            println("request: I'm done and I don't explicitly join my children that are still active")
        }
        println("Calling to join all the children jobs")
        request.join() // wait for completion of the request, including all its children
        println("Now processing of the request is complete")
    }




    suspend fun launchCoroutines() {
            GlobalScope.launch(coroutineContext) {
                println("--Launch-- start : ${CoroutinesExample.getThreadName()}")
                Thread.sleep(1000)
                println("--Launch-- ended : ${CoroutinesExample.getThreadName()}")
            }

            run {
                println("--Run----- start : ${CoroutinesExample.getThreadName()}")
                Thread.sleep(200)
                println("--Run----- ended : ${CoroutinesExample.getThreadName()}")
            }

    }


    ////Todo https://medium.com/androiddevelopers/cancellation-in-coroutines-aa6b90163629
    //Todo https://kotlinlang.org/docs/reference/coroutines/coroutine-context-and-dispatchers.html#thread-local-data
    //Todo https://stackoverflow.com/questions/50230466/kotlin-withcontext-vs-async-await

}