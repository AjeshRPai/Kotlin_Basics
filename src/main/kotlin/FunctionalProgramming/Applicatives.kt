package FunctionalProgramming

import arrow.core.Option
import arrow.core.Some
import arrow.core.Tuple3
import arrow.core.extensions.option.applicative.applicative
import arrow.core.fix


/**
 * The Applicative typeclass abstracts the ability to lift values and apply functions over the computational context of a type constructor.
 * Examples of type constructors that can implement instances of the Applicative typeclass include Option, NonEmptyList, List, and many other datatypes that include a just and either ap function.
 * ap may be derived for monadic types that include a Monad instance via flatMap.
 * */

fun profileService(): Option<String> = Some("Alfredo Lambda")
fun phoneService(): Option<Int> = Some(55555555)
fun addressService(): Option<List<String>> = Some(listOf("1 Main Street", "11130", "NYC"))


data class Profile(val name: String, val phone: Int, val address: List<String>)


fun main() {
    val r: Option<Tuple3<String, Int, List<String>>> = Option.applicative().tupled(profileService(), phoneService(), addressService()).fix()
    print(r.map { Profile(it.a, it.b, it.c) })
}