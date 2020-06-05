package FunctionalProgramming

import arrow.core.Tuple2
import arrow.core.toT
import arrow.optics.Iso
import arrow.optics.Optional
import arrow.optics.optics


@optics data class Street(val number: Int, val name: String) {
    companion object
}
@optics data class Address(val city: String, val street: Street){
    companion object
}
@optics data class Company(val name: String, val address: Address){
    companion object
}
@optics data class Employee(val name: String, val company: Company?){
    companion object
}


val john = Employee("John Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))

val optional: Optional<Employee, String> = Employee.company.address.street.name


// https://speakerdeck.com/heyitsmohit/functional-programming-with-arrow?slide=52


data class Point2D(val x: Int, val y: Int)

val pointIsoTuple: Iso<Point2D, Tuple2<Int, Int>> = Iso(
        get = { point -> point.x toT point.y },
        reverseGet = { tuple -> Point2D(tuple.a, tuple.b) }
)

fun main() {
    print(optional.modify(john, String::toUpperCase))

    val point = Point2D(6, 10)
    print(point)

    val tuple = pointIsoTuple.get(point)
    print(tuple)

    print(pointIsoTuple.reverseGet(tuple))

    //Using an Iso, we can modify our source S with a function that works on our focus A.
    val addFive: (Tuple2<Int, Int>) -> Tuple2<Int, Int> = { tuple2 -> (tuple2.a + 5) toT (tuple2.b + 5) }

    print(pointIsoTuple.modify(point, addFive))

   // A function (A) -> A can be lifted to a function (S) -> S
    val liftedAddFive: (Point2D) -> Point2D = pointIsoTuple.lift(addFive)

    print(liftedAddFive(point))
}


