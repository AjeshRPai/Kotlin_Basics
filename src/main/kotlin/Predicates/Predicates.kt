package Predicates

object Predicates {

    @JvmStatic
    fun main(args:Array<String>){

        val myPredicate = {num:Int-> num>10}

        val numbers= listOf(1,2,3,4,10,60,70)

        val allgreaterthan=numbers.all(myPredicate)
        println(allgreaterthan)

        val isAny=numbers.any { it>90 }
        println(isAny)

        val count=numbers.count { it>10 }
        println(count)
    }
}