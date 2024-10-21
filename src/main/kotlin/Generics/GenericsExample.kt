package Generics

fun main() {
    val anyConsumer: Consumer<Any> = Consumer()
    // Contravariance: Consumer<Any> can accept a Consumer<String>
    val stringConsumer: Consumer<String> = anyConsumer

    stringConsumer.consume("Hello")  // Prints: Consumed: Hello

    val stringProducer: Producer<String> = Producer("Hello")
    // Covariance: Producer<String> is a subtype of Producer<Any>
    val anyProducer: Producer<Any> = stringProducer

    println(anyProducer.produce())  // Prints: Hello

    val stringInvariant: Invariant<String> = Invariant("Hello")
    // Invariance: No type substitution allowed
    // val anyInvariant: Invariant<Any> = stringInvariant  // Compile error!

    stringInvariant.consume("World")
    println(stringInvariant.produce())  // Prints: World


    // example for api handler
    // val handler: ApiHandler<ApiRequest, ApiResponse> = ApiHandler<UserApiRequest, UserApiResponse>()
}


// covariant function
// T Can only be produced
class Producer<out T>(private val value: T) {
    fun produce(): T {
        return value
    }
}


// Contravariant class: 'T' can only be consumed (used as input)
class Consumer<in T> {
    fun consume(item: T) {
        println("Consumed: $item")
    }
}

// Invariant class: 'T' can both be consumed and produced, but no subtyping allowed
class Invariant<T>(private var value: T) {
    fun produce(): T {
        return value
    }

    fun consume(item: T) {
        value = item
    }
}

abstract class ApiRequest{
}

class UserApiRequest

open class ApiResponse {

}

class UserApiResponse : ApiResponse()
class ErrorApiResponse : ApiResponse()

//
// Use case for in and out modifier
//
class ApiHandler<in Request : ApiRequest, out Response : ApiResponse>

// Use case for making interface invariant
interface DataMapper<Entity, DTO> {
    fun mapToDTO(entity: Entity): DTO
    fun mapToEntity(dto: DTO): Entity
}


