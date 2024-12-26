package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

val flow = flow {
    var i = 0
    while (i < 5) {
        println("Producer: $i")
        emit(i)
        i++
        delay(10)
    }
}

fun main() {
    runBlocking {
        flow.collect {
            delay(100) // Simulate long processing
            println("Consumer: $it")
        }
    }
}