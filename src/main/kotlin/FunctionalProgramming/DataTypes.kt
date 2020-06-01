package FunctionalProgramming

import arrow.core.Either
import arrow.core.firstOrNone
import arrow.core.toOption

//data types

// A datatype is a class that encapsulates one reusable coding pattern.
// These solutions have a canonical implementation that is generalized for all possible uses.

// Data types are a design pattern in functional programming that contains
// a Reusable Function. These functions follow a rule in all the data types

// Datatypes work over themselves,
// never directly over the values defined by its generic parameters.


// Option type example

val foxMap = mapOf(1 to "The", 2 to "Quick", 3 to "Brown", 4 to "Fox")

val ugly = foxMap.entries.firstOrNull { it.key == 5 }?.value.let { it?.toCharArray() }.toOption()
val pretty = foxMap.entries.firstOrNone { it.key == 5 }.map { it.value.toCharArray() }


//Either type example

fun parse(s: String): Int =
    if (s.matches(Regex("-?[0-9]+"))) s.toInt()
    else throw NumberFormatException("$s is not a valid integer.")

fun reciprocal(i: Int): Double =
    if (i == 0) throw IllegalArgumentException("Cannot take reciprocal of 0.")
    else 1.0 / i

fun stringify(d: Double): String = d.toString()


fun parseEither(s: String): Either<NumberFormatException, Int> =
    if (s.matches(Regex("-?[0-9]+"))) Either.Right(s.toInt())
    else Either.Left(NumberFormatException("$s is not a valid integer."))

fun reciprocalEither(i: Int): Either<IllegalArgumentException, Double> =
    if (i == 0) Either.Left(IllegalArgumentException("Cannot take reciprocal of 0."))
    else Either.Right(1.0 / i)

fun stringifyEither(d: Double): String = d.toString()

fun main() {
    println("ugly = $ugly")
    println("pretty = $pretty")


    val notANumber = parse("Not a number")
    println("notANumber = ${notANumber}")

    val number2 = parse("2")
    println("notANumber = ${notANumber}")

}



//https://medium.com/javascript-scene/the-forgotten-history-of-oop-88d71b9b2d9f

//https://www.raywenderlich.com/324105-functional-programming-with-kotlin-and-arrow-part-2-categories-and-functors