package Delegation

import kotlin.reflect.KProperty

class LoggingProperty<T>(var value: T) {
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        println("${prop.name} getter returned $value")
        return value
    }

    operator fun setValue(
        thisRef: Any?,
        prop: KProperty<*>,
        newValue: T
    ) {
        println("${prop.name} changed from $value to $newValue")
        value = newValue
    }
}

class LoggingPropertyProvider<T>(
    private val value: T
) {
    operator fun provideDelegate(
        thisRef: Any?,
        property: KProperty<*>
    ): LoggingProperty<T> = LoggingProperty(value)
}


var token1: String? by LoggingPropertyProvider(null)
var attempts1: Int by LoggingPropertyProvider(0)

var token: String? by LoggingProperty(null)
var attempts: Int by LoggingProperty(0)

fun main() {
    token = "AAA" // token changed from null to AAA
    val res = token // token getter returned AAA
    println(res) // AAA
    attempts++
// attempts getter returned 0
// attempts changed from 0 to 1

    token1 = "AAA2" // token changed from null to AAA2
    val res1 = token1 // token getter returned AAA2
    println(res1) // AAA2
    attempts1 ++
}