package Coroutines

import kotlinx.coroutines.*

object CoroutinesExample {

    @JvmStatic
    fun main(args: Array<String>) {
        //runBlockingTest1()
//        launchWithoutContext()
        launchWithContext()
    }

    fun testFunction1(){
        GlobalScope.launch {
            print("The value of 5 is ${square(5)}\n")
        }
    }

    suspend fun square(i: Int): Int {
        delay(500)
        return i * i
    }

    fun ThreadVsRunSameThread(){
        /***
         *  Here Thread is Started with Run Command and it runs in the Same Thread
         *  Both the thread and Run executes sequentially and on the same Thread
         *
         */
        Thread {
            println("-Thread--- start : ${getThreadName()}")
            Thread.sleep(1000)
            println(" -Thread--- ended : ${getThreadName()}")
        }.run()

        run {
            println(" -Run------ start : ${getThreadName()}")
            Thread.sleep(200)
            println(" -Run------ ended : ${getThreadName()}")
        }
    }

    fun ThreadVsRunDifferentThread() {
        /**
         * Here the Thread and the Run command is executed Parallely in different threads
         * as the Start Command is used to start the Thread
         *
         * */

        Thread {
            println("-Thread--- start : ${getThreadName()}")
            Thread.sleep(1000)
            println("-Thread--- ended : ${getThreadName()}")
        }.start()

        run {
            println("-Run------ start : ${getThreadName()}")
            Thread.sleep(200)
            println("-Run------ ended : ${getThreadName()}")
        }

    }

    fun runBlockingTest1() {
        runBlocking {
            println("-Blocking- start : ${getThreadName()}")
            Thread.sleep(1000)
            println("-Blocking- ended : ${getThreadName()}")
        }

        run {
            println("-Run------ start : ${getThreadName()}")
            Thread.sleep(200)
            println("-Run------ ended : ${getThreadName()}")
        }
    }

    fun runBlockingTest2() {
        runBlocking(Dispatchers.Default) {
            println("-Blocking- start : ${getThreadName()}")
            Thread.sleep(1000)
            println("-Blocking- ended : ${getThreadName()}")
        }

        run {
            println("-Run------ start : ${getThreadName()}")
            Thread.sleep(200)
            println("-Run------ ended : ${getThreadName()}")
        }
    }

    fun launchWithoutContext() {
        GlobalScope.launch {
            println("--Launch-- start : ${getThreadName()}")
            Thread.sleep(300)
            println("--Launch-- ended : ${getThreadName()}")
        }

        run {
            println("--Run----- start : ${getThreadName()}")
            Thread.sleep(200)
            println("--Run----- ended : ${getThreadName()}")
        }
    }

    fun launchWithContext() {
        runBlocking {
            launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                Thread.sleep(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }

            run {
                println("--Run----- start : ${getThreadName()}")
                Thread.sleep(200)
                println("--Run----- ended : ${getThreadName()}")
            }
        }
    }

    fun launchWithJoin() {
        runBlocking {
            launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                Thread.sleep(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }.join()

            run {
                println("--Run----- start : ${getThreadName()}")
                Thread.sleep(200)
                println("--Run----- ended : ${getThreadName()}")
            }
        }
    }

    fun launchWithJoinLater() {
        runBlocking {
            val job = launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                Thread.sleep(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }

            run {
                println("--Run----- start : ${getThreadName()}")
                Thread.sleep(200)
                job.join()
                println("--Run----- ended : ${getThreadName()}")
            }
        }
    }

    fun launchWithDelay() {
        runBlocking {
            launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                delay(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }

            run {
                println("--Run----- start : ${getThreadName()}")
                delay(200)
                println("--Run----- ended : ${getThreadName()}")
            }
        }
    }

    fun launchWithDelayJoin() {
        runBlocking {
            launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                delay(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }.join()

            run {
                println("--Run----- start : ${getThreadName()}")
                delay(200)
                println("--Run----- ended : ${getThreadName()}")
            }
        }
    }

    fun launchWithJob() {
        runBlocking {
            val job = launch(coroutineContext) {
                println("--Launch-- start : ${getThreadName()}")
                delay(1000)
                println("--Launch-- ended : ${getThreadName()}")
            }

            run {
                println("--Run----- start : ${getThreadName()}")
                delay(200)
                job.cancel()
                println("--Run----- ended : ${getThreadName()}")
            }
        }
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
        // Thus is run inside same Thread and it merely blocks the Thread
        // and Doesnt let the thread to run anything else
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
        // uses delay to show the Suspended Behaviour
        // This is run inside the same Thread
        // When Delay is used the Function is Suspended and the Thread Goes back to pool
        // So in this case the run is executed first and then delay suspends the Thread but doesnt block the thread
        // So launch starts and suspends the Thread again and then run ends since the delay is already finished
        // Then launch ends
        // In which case launch and run will run concurrently

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