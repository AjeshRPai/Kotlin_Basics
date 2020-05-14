package Function


/**
 * Memoization is a concept of
 * storing the values of the Function
 * so that it shouldnt be computed later
 *
 * In this example the sum of factors are
 * memoized so that it isn't executed everytime
 *
 */

object Memoize {
    @JvmStatic
    fun main(args: Array<String>) {
        memoizedSumFactors(3)
        memoizedSumFactors(3)
        memoizedSumFactors(4)
        memoizedSumFactors(4)
    }

    fun sumOfFactors(number: Int) {
        println("Running Sum of factors of "+number)
        factorsOf(number).sum()
    }

    fun factorsOf(number: Int) = (1 to number).toList().filter { isFactor(number, it) }

    fun isFactor(number: Int, potential: Int) = number % potential == 0

    val memoizedSumFactors = { x: Int -> sumOfFactors(x) }.memoize()

}

class Memoize1<in T, out R>(val f: (T) -> R) : (T) -> R {
    private val values = mutableMapOf<T, R>()
    override fun invoke(x: T): R {
        return values.getOrPut(x, { f(x) })
    }
}

fun <T, R> ((T) -> R).memoize(): (T) -> R = Memoize1(this)
