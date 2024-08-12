package coroutines.bestPractices

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class NotificationsSender1(
    private val client: NotificationsClient,
    private val notificationScope: CoroutineScope,
) {
    fun sendNotifications(
        notifications: List<Notification>
    ) {
        for (n in notifications) {
            notificationScope.launch {
                client.send(n)
            }
        }
    }
}
class NotificationsSender2(
    private val client: NotificationsClient,
) {
    suspend fun sendNotifications(
        notifications: List<Notification>
    ) = supervisorScope {
        for (n in notifications) {
            launch {
                client.send(n)
            }
        }
    }
}

class NotificationsClient{
    fun send(notification: Notification){
        println("messsage")
    }
}

data class Notification(
    val message: String = "message"
)

suspend fun main() {
    val scope = CoroutineScope(Job())
    val notificationClient = NotificationsClient()
    val listOfNotifications = listOf(
        Notification(),
        Notification(),
        Notification(),
        Notification(),
        Notification()
    )
    val notificationsSender1 = NotificationsSender1(notificationClient, notificationScope = scope )
    val notificationsSender2 = NotificationsSender2(notificationClient)
    notificationsSender1.sendNotifications(listOfNotifications)
    notificationsSender2.sendNotifications(listOfNotifications)
}