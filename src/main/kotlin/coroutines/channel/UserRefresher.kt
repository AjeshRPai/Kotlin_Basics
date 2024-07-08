package coroutines.channel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class UserRefresher(
    private val scope: CoroutineScope,
    private val refreshData: suspend (Int) -> Unit,
) {
    private var refreshJob: Job? = null

    private val channel = Channel<Int>()

    init {
        scope.launch {
            for(userId in channel){
                refreshData(userId)
            }
        }
    }

    suspend fun refresh(userId: Int) {
       channel.send(userId)
    }
}