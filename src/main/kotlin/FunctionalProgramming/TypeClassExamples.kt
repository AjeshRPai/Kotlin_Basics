package FunctionalProgramming

import arrow.core.Option
import arrow.core.Some
import arrow.typeclasses.Show
import arrow.core.extensions.*
import arrow.typeclasses.Eq

// Typeclasses are interfaces that define a set of extension functions associated to one type.
// You may see them referred to as “extension interfaces."

// The purpose of these interface are meant to be implemented outside of their types.


// The Show typeclass abstracts the ability to obtain a String representation of any object.
// It can be considered the typeclass equivalent of Java’s Object#toString.

class Person(val firstName: String, val lastName: String)
val personShow = Show<Person> { "Hello $firstName $lastName" }

fun typeClassExample() {
    print(personShow.run {
        Person("Jon","Snow")
    })
}

// The Eq typeclass abstracts the ability to compare two instances of any object.
// It can be considered the typeclass equivalent of Java’s Object#equals.

val comparePerson = Eq<Person> { a, b -> a.firstName == b.firstName }

fun eqExample() {
    val person1 = Person("John","dane")
    val person2 = Person("John","doe")

    print(comparePerson.run { person1.eqv(person2)})

}

fun main() {
    eqExample()
}



