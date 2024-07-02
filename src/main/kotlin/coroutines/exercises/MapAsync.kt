package coroutines.exercises

import kotlinx.coroutines.*

suspend fun <T, R> Iterable<T>.mapAsync(
    transformation: suspend (T) -> R
): List<R> = coroutineScope {
    map { async { transformation(it) } }
        .awaitAll()
}
