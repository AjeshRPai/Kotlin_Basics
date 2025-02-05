package strings

import kotlin.test.assertEquals

fun spinWords(sentence: String): String {
    // Your code goes here!
    val letters = sentence.split(" ")
    var output = ""
    letters.forEach { word ->
        val toAppend = if(word.length>5) word.reversed() else word
        output = output.plus(toAppend).plus(" ")
    }
    return output
}

fun main() {
    val word = spinWords("You are tsomla to the last test")
    println(word)
}


