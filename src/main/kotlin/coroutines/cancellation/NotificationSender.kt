package coroutines.cancellation

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NotificationSender(
    private val client: NotificationClient,
    private val exceptionCollector: ExceptionCollector,
    dispatcher: CoroutineDispatcher,
) {
    val exceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable -> exceptionCollector.collectException(throwable) }

    val context: CoroutineContext = SupervisorJob() + dispatcher + exceptionHandler

    val scope = CoroutineScope(context)

    fun sendNotifications(notifications: List<Notification>) {
            notifications.forEach { notification ->
                scope.launch {
                    client.send(notification)
                }
        }
    }

    fun cancel() {
        context.cancelChildren()
    }
}

data class Notification(val id: String)

interface NotificationClient {
    suspend fun send(notification: Notification)
}

interface ExceptionCollector {
    fun collectException(throwable: Throwable)
}