package InlineFunctionType

import java.util.concurrent.TimeUnit

/***
 * https://android.jlelse.eu/inline-noinline-crossinline-what-do-they-mean-b13f48e113c2
 * * **/

object InlineTimeMeasure {

    @JvmStatic
    fun main(args: Array<String>){
        localVariableModification()

    }


    private fun localVariableModification(){
        var a = 0
        measureExecution("Finished doing work inline") {
            repeat(100_000_000) {
                a += 1
            }
        }
        var b = 0
        measureExecution("Finished doing work NOinline") {
            noinlineRepeat(100_000_000) {
                b += 1
            }
        }
    }

    inline fun repeat(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }

    fun noinlineRepeat(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }

    inline fun <T> measureExecution(logMessage: String, function: () -> T): T {
        val startTime = System.nanoTime()
        return function.invoke().also {
            val difference = System.nanoTime() - startTime
            println( "$logMessage: took ${TimeUnit.NANOSECONDS.toMillis(difference)} ms")
        }
    }
}