package enum

import kotlin.enums.enumEntries

enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumEntries<T>().joinToString { it.name })
}

inline

fun main() {
    printAllValues<RGB>()


    (1 until 5)

}

fun String?.foo() {
    this@foo
}

// RED, GREEN, BLUE

open class Box<T>

val test: Int by materializeDelegate()

fun <T: CharSequence> materializeDelegate(): Box<T> = TODO()

operator fun <K: Comparable<K>> Box<K>.provideDelegate(receiver: Any?, property: kotlin.reflect.KProperty<*>): K = TODO()

operator fun <Q: Comparable<Q>> Q.getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): Q = TODO()


@ConsistentCopyVisibility
data class User private constructor(val name: String, val id: Int){
    companion object{
        fun of(name:String, id: Int): User{
            return User(if(name.isEmpty()) "Unknown" else name, id)
        }
    }
}

fun getUser(): User {
    // returns user with empty name, which by default is impossible.
    return User.of("Alex", 1).copy(name="", id=2)
}

import kotlin.reflect.KProperty

class Constant<T>(val value: T) {
    inline operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    inline operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {}
}

fun main() {
    val a: String? = null
    var b by Constant<Int?>(1)
    b = a?.length // rhs must be a final val property with a default getter
    if (b != null) a.length // NPE
}