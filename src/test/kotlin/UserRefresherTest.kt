import coroutines.channel.UserRefresher
import kotlinx.coroutines.*
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test
import kotlin.time.measureTime


class UserRefresherTest {
    @Test
    fun `should finish all refreshes`(): Unit = runTest {
        val refreshed = ConcurrentHashMap.newKeySet<Int>()
        val finished = AtomicInteger(0)
        val userRefresher = UserRefresher(
            scope = backgroundScope,
            refreshData = { userId ->
                refreshed += userId
                finished.incrementAndGet()
            }
        )

        coroutineScope {
            repeat(1000) {
                launch { userRefresher.refresh(it) }
            }
        }
        await { finished.get() >= 1000 }
        assertEquals(1000, refreshed.size)
    }

    @Test
    fun `should not start more than one refresh job`(): Unit = runTest {
        val finished = AtomicInteger(0)
        val userRefresher = UserRefresher(
            scope = backgroundScope,
            refreshData = { userId ->
                delay(1000)
                finished.incrementAndGet()
            }
        )

        coroutineScope {
            repeat(1000) {
                launch { userRefresher.refresh(it) }
            }
        }
        assert(currentTime <= 1000)
        await { finished.get() >= 1000 }
        assertEquals(1000 * 1000, currentTime)
    }

    @Test
    fun `should not start more than one refresh job (on real time)`(): Unit = runBlocking(Dispatchers.Default) {
        val finished = AtomicInteger(0)
        val backgroundScope = CoroutineScope(Job())
        val userRefresher = UserRefresher(
            scope = backgroundScope,
            refreshData = { userId ->
                delay(20)
                finished.incrementAndGet()
            }
        )

        val sendTime = measureTime {
            coroutineScope {
                repeat(100) {
                    launch { userRefresher.refresh(it) }
                }
            }
        }
        val executionTime = measureTime {
            await { finished.get() >= 100 }
        }
        assertEquals(0, sendTime.inWholeSeconds)
        assertEquals(2, executionTime.inWholeSeconds)
        backgroundScope.cancel()
    }
}

suspend fun await(condition: () -> Boolean) {
    while (!condition()) { delay(1) }
}