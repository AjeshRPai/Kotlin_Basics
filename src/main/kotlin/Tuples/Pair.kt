package Tuples

object Pair {
    @JvmStatic
    fun main(args: Array<String>) {

        val pair=Pair("first","last")

        val (name,last)=pair

        println("first value="+name)

        println("second value="+last)

    }


}