package Tuples

object Triple {

    @JvmStatic
    fun main(args: Array<String>) {

        val pair=Triple("first","middle","last")

        val (name,middle,last)=pair

        println("first value="+name)

        println("second value="+middle)

        println("last value="+last)

    }

}