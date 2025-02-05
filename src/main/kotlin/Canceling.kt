import kotlinx.coroutines.*

private val dispatcher = Dispatchers.IO.limitedParallelism(1)


class CancellingRefresher(
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    suspend fun refresh() {
        job?.cancel()
        job = scope.launch {
            delay(1000) // Simulating refresh logic
            println("the message is printed")
        }
    }
}

fun main() = runBlocking {
    val refresher = CancellingRefresher(this)

    coroutineScope {
        // Launching two coroutines in parallel to invoke refresh()
        repeat(1000) {
            launch { refresher.refresh() }
        }
        delay(1000)
        repeat(1000) {
            launch { refresher.refresh() }
        }
        delay(1000)
        repeat(1000) {
            launch { refresher.refresh() }
        }
        // Waiting for both jobs to complete

    }

    println("Both refresh tasks are complete.")
}

