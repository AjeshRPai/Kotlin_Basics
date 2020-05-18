package Coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Coroutines {

    @JvmStatic
    fun main(args: Array<String>) {
        GlobalScope.launch {
            print("The value of 5 is ${square(5)}\n")
        }
        Thread.sleep(1000)
        testRunFunction1()
        testRunFunction2()
        testRunFunction3()
    }

    suspend fun square(i: Int): Int {
        delay(500)
        return i * i
    }

    fun testRunFunction1() {
        // Start a coroutine
        // Shows what happens on a normal case
        GlobalScope.launch{
            println("launch start : ${getThreadName()}")
            Thread.sleep(200)
            println("launch ended : ${getThreadName()}")
        }

        run {
            println("run start: ${getThreadName()}")
            Thread.sleep(300)
            println("run ended: ${getThreadName()}")
        }
    }

    fun testRunFunction2() {
        //uses Thread.sleep to show the Blocked behaviour
        runBlocking {
            // Start a coroutine
            launch(coroutineContext) {
                println("launch start : ${getThreadName()}")
                Thread.sleep(200)
                println("launch ended : ${getThreadName()}")
            }

            run {
                println("run start: ${getThreadName()}")
                Thread.sleep(300)
                println("run ended: ${getThreadName()}")
            }
        }
    }

    fun testRunFunction3() {
        //uses delay to show the Suspended Behaviour
        runBlocking {
            // Start a coroutine
            launch(coroutineContext) {
                println("launch start : ${getThreadName()}")
                delay(200)
                println("launch ended : ${getThreadName()}")
            }

            run {
                println("run start: ${getThreadName()}")
                delay(300)
                println("run ended: ${getThreadName()}")
            }
        }
    }


    fun getThreadName():String{
        return Thread.currentThread().name.toUpperCase()
    }



}