package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

suspend fun main() {
    println(zipExample().toList())
}


fun zipExample(): Flow<String> {
    val flow1 = flowOf("A")
    val flow2 = flowOf(1, 2, 3)
    val result = flow1.combine(flow2) { num1, num2 ->
        num1 + num2
    }
    return result
}




