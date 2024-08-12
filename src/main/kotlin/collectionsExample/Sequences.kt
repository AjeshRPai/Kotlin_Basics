package collectionsExample

import InlineFunctionType.InlineTimeMeasure.measureExecution

/**
 * This is from the link
 *
 * https://typealias.com/guides/kotlin-sequences-illustrated-guide/
 * https://proandroiddev.com/sequences-a-pragmatic-approach-9d4296086a9d
 *https://blog.kotlin-academy.com/effective-kotlin-use-sequence-for-bigger-collections-with-more-than-one-processing-step-649a15bb4bf
 *
 * */
object Sequences{


    @JvmStatic
    fun main(args: Array<String>) {

        // 5. Collect them into their final box

        val list = arrayListOf<Int>()

        for (i in 1..100000000){
            list.add(i)
        }

        measureExecution("withCollection(list) ") { withCollection(list) }

        measureExecution("withSequence(list)") { withSequence(list) }

    }

    private fun withCollection(list:List<Int>): Int {
        return list
                .filter { it%2==0 }
                .map { it + 10 }
                .first{ it % 10 == 0 }
    }

    private fun withSequence(list: List<Int>) {
         list.asSequence()
                 .filter { it%2==0 }
                 .map { it + 10 }
                 .first{ it % 10 == 0  }
    }
}





data class Crayon(
        val color: String,
        val label: String? = null
)

val BigBoxOfCrayons = setOf(
        Crayon("marigold"),
        Crayon("lime"),
        Crayon("red"),
        Crayon("yellow"),
        Crayon("blue"),
        Crayon("orange"),
        Crayon("grape"),
        Crayon("white"),
        Crayon("red-violet"),
        Crayon("pond"),
        Crayon("cinnamon"),
        Crayon("neon lightning"),
        Crayon("metal"),
        Crayon("violet"),
        Crayon("charcoal"),
        Crayon("brick"),
        Crayon("green"),
        Crayon("silver")
)

val BigBoxOfCrayons2 = setOf(
        Crayon("marigold"),
        Crayon("lime"),
        Crayon("red"),
        Crayon("yellow"),
        Crayon("blue"),
        Crayon("orange"),
        Crayon("grape"),
        Crayon("white"),
        Crayon("red-violet"),
        Crayon("pond"),
        Crayon("cinnamon"),
        Crayon("neon lightning"),
        Crayon("metal"),
        Crayon("violet"),
        Crayon("charcoal"),
        Crayon("brick"),
        Crayon("green"),
        Crayon("silver")
)

val includedColors =
        setOf("brick", "marigold", "neon lightning", "orange", "red", "red-violet", "white", "yellow")


