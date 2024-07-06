package coroutines.synchronized

import kotlinx.coroutines.*

class CancellingRefresher(
    private val scope: CoroutineScope,
    private val refreshData: suspend () -> Unit,
) {
    private var refreshJob: Job? = null

    private var dispatcher = Dispatchers.Default.limitedParallelism(1)

    suspend fun refresh() = withContext(dispatcher) {
        refreshJob?.cancel()
        refreshJob = scope.launch {
            refreshData()
        }
    }

    // with dispatcher 2 sec 411 ms
    //  with mutex 2 sec 607 ms
    // with synchronized 2 sec 646 ms
}