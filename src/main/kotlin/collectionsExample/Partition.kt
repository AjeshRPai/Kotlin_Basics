package collectionsExample


/**
 * @link
 * https://medium.com/@agrawalsuneet/partition-kotlin-7bb3c94c113c
 * **/

object Partition{

    @JvmStatic
    fun main(args: Array<String>) {
        val list = listOf(1 , 2, 3, 4, 5)
        val (even, odd) = list.partition{ it % 2 == 0}

        println(even)
        println(odd)
    }
}