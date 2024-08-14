package Generics

import java.time.Instant

fun main() {
    val empty: List<Nothing> = emptyList()
    val strs: List<String> = empty
    val ints: List<Int> = empty
    val other: List<Char> = emptyList()
    println(empty === other) // true
}

data class Task<T>(
    val id: String,
    val scheduleAt: Instant,
    val data: T,
    val priority: Int,
    val maxRetries: Int? = null
)

