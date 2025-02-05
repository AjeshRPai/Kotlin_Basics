import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/*
* Reference - https://kt.academy/article/cc-flatmap
* */

fun flowFrom(elem: String) = flowOf(1, 2, 3)
    .onEach { delay(1000) }
    .map { "${it}_${elem} " }


suspend fun flatMap() {
    flowOf("A", "B", "C")
        .flatMapConcat { flowFrom(it) }
        .collect { println(it) }
}

// (1 sec)
// 1_A
// (1 sec)
// 2_A
// (1 sec)
// 3_A
// (1 sec)
// 1_B
// (1 sec)
// 2_B
// (1 sec)
// 3_B
// (1 sec)
// 1_C
// (1 sec)
// 2_C
// (1 sec)
// 3_C

suspend fun flatMapMerge() {
    flowOf("A", "B", "C")
        .flatMapMerge { flowFrom(it) }
        .collect { println(it) }
}

// (order may vary)
// (1 sec)
// 1_A
// 1_B
// 1_C
// (1 sec)
// 2_A
// 2_B
// 2_C
// (1 sec)
// 3_A
// 3_B
// 3_C


suspend fun flatMapLatest() {
    flowOf("A", "B", "C")
        .flatMapLatest { flowFrom(it) }
        .collect { println(it) }
}

suspend fun main() {
    flatMapLatest()
}

