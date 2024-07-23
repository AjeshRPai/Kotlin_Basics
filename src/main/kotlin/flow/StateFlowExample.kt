package flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    val mutableStateFlow =
        MutableStateFlow("Initial value")

    launch {
        mutableStateFlow.collect {
            println("#1 received $it")
        }
    }
    launch {
        mutableStateFlow.collect {
            println("#2 received $it")
        }
    }
    delay(1000)
    mutableStateFlow.emit("Message1")
    mutableStateFlow.emit("Message2")

    delay(2000)
    launch {
        mutableStateFlow.collect{
            println("#3 received $it")
        }
    }
}
