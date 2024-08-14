package Generics

// Contravariant
class Encapsulation<in T>
open class Parent
class Child : Parent()
fun main() {
    val eg: Parent = Child() // Child is a subtype of Parent
    val eg1: Encapsulation<Parent> = Encapsulation<Child>() // Error: Type mismatch
    val eg2: Encapsulation<Child> = Encapsulation<Parent>() // fine
    val eg3: Encapsulation<Number> = Encapsulation<Int>() // Error: Type mismatch
    val eg4: Encapsulation<Int> = Encapsulation<Number>() // fine
}