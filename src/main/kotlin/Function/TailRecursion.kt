package Function

/**
 * This is the Tail Recursion function as you can see
 * here that the result is passed to the function on
 * recursion to avoid stack overflow
 *
 */

object TailRecursion {
    @JvmStatic
    fun main(args: Array<String>) {
         factorial(12345.0)
    }

    private fun factorial(number: Double):Double{
        return factorial(number,1.0)
    }

    tailrec fun factorial(number:Double,result:Double): Double {
        when(number) {
             0.0 -> return result
             else -> return factorial(number-1,number*result)
        }
    }

}