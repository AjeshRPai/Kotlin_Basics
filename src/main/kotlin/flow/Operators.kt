package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .debounce(1000)
        .collect { value ->
            println(value)
        }

    // prints the all the items in 300 mis
    flowOf("A", "B", "C")
        .onEach  { delay(300) }
        .buffer(3)
        .collect { println("$it") }
}