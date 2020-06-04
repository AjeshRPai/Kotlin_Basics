package FunctionalProgramming

import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe


//Arrow Fx is a next-generation Typed FP Effects Library that makes effectful
// and polymorphic programming first class in Kotlin,
// and acts as an extension to the Kotlin native suspend system.


// A pure function is a function that consistently returns the same output when given the same input.
// Pure functions exhibit a deterministic behavior and cause no observable effects externally.
// We call this property referential transparency.


//A side effect is an externally observable effect a function performs in addition to returning a value.


//Performing network or file IO, writing to streams, and, in general,
// all functions that return Unit are very likely to produce side effects.
// That’s because a Unit return value denotes no useful return,
// which implies that the function does nothing but perform effects.


// effect wraps the side effect and turns it into a pure value by
// lifting any suspend () -> A user-declared side effect into an IO<A> value.

// Once u purify a side effect with effect, you can extract its value in a non blocking way.
// !effect takes the suspended side effects and takes control of the continuation context
// before they get a chance to be executed .

// How does it ensure that they are referentially transparent


// it ensures that they will only run at edge
// running the greet function without unsafe run type class in arrow wont produce any result

// Since both blocking and non-blocking execution scenarios perform side effects,
// we consider running effects as an UNSAFE operation.
//
// Arrow restricts the ability to run programs to extensions of the UNSAFERUN type class.
//
// Usage of unsafe is reserved for the end of the world and
// may be the only impure execution of a well-typed functional program

suspend fun sayHello(): Unit =
        println("Hello World")

suspend fun sayGoodBye(): Unit =
        println("Good bye World!")

fun greet(): IO<Unit> =
        IO.fx {
            !effect { sayHello() }
            !effect { sayGoodBye() }
        }

fun main() {
    unsafe{ runBlocking { greet()} }
}