package Generics

import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

// covariance use case scenario's
data class TaskUpdate<T>(
    val id: TaskPropertyUpdate<String> = Keep,
    val scheduleAt: TaskPropertyUpdate<Instant> = Keep,
    val data: TaskPropertyUpdate<T> = Keep,
    val priority: TaskPropertyUpdate<Int> = Keep,
    val maxRetries: TaskPropertyUpdate<Int?> = Keep
)
sealed interface TaskPropertyUpdate<out T>
data object Keep : TaskPropertyUpdate<Nothing>
data class ChangeTo<T>(val newValue: T) : TaskPropertyUpdate<T>
data object RestorePrevious : TaskPropertyUpdate<Nothing>
data object RestoreDefault : TaskPropertyUpdate<Nothing>

val update = TaskUpdate<String>(
    data = ChangeTo("ABC"),
    maxRetries = RestorePrevious,
    priority = RestoreDefault,
)

sealed class Either<out L, out R>
data class Left<out L>(val value: L) : Either<L, Nothing>()
data class Right<out R>(val value: R) : Either<Nothing, R>()