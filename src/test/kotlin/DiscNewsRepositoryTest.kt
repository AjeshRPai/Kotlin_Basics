import coroutines.dispatcher.DiscNewsRepository
import coroutines.dispatcher.DiscReader
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlin.test.Test
import kotlin.test.assertEquals


@Suppress("FunctionName")
class DiscNewsRepositoryTest {

    @Test
    fun `should read data from disc using DiscReader`() = runBlocking {
        val repo = DiscNewsRepository(OneSecDiscReader(listOf("Some title", "Some content")))
        val res = repo.getNews("SomeUserId")
        assertEquals("Some title", res.title)
        assertEquals("Some content", res.content)
    }

    class ImmediateDiscReader(val map: Map<String, List<String>>) : DiscReader {
        override fun read(key: String): List<String> = map[key] ?: error("Element not found")
    }

    @Test
    fun `should be prepared for many reads at the same time`() = runBlocking<Unit> {
        val repo = DiscNewsRepository(OneSecDiscReader(listOf("Some title", "Some content")))
        val time = measureTimeMillis {
            coroutineScope {
                repeat(10) { id ->
                    launch {
                        repo.getNews("SomeUserId$id")
                    }
                }
            }
        }
        assert(time < 2000) { "Should take less than 2000, took $time" }
    }

    @Test
    fun `should be prepared for 200 parallel reads`() = runBlocking<Unit> {
        val repo = DiscNewsRepository(OneSecDiscReader(listOf("Some title", "Some content")))
        val time = measureTimeMillis {
            coroutineScope {
                repeat(200) { id ->
                    launch {
                        repo.getNews("SomeUserId$id")
                    }
                }
            }
        }
        assert(time < 1900) { "Should take less than 2000, took $time" }
    }

    @Test
    fun `should not allow more than 200 parallel reads`() = runBlocking<Unit> {
        val repo = DiscNewsRepository(OneSecDiscReader(listOf("Some title", "Some content")))
        val time = measureTimeMillis {
            coroutineScope {
                repeat(201) { id ->
                    launch {
                        repo.getNews("SomeUserId$id")
                    }
                }
            }
        }
        assert(time > 2000) { "Should take less than 2000, took $time" }
    }

    class OneSecDiscReader(private val response: List<String>) : DiscReader {
        override fun read(key: String): List<String> {
            Thread.sleep(1000)
            return response
        }
    }
}