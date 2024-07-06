import coroutines.synchronized.FakeNetworkService
import coroutines.synchronized.UserDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals


class UserDownloaderTest {
    @Test
    fun test() = runBlocking {
        val downloader = UserDownloader(FakeNetworkService())
        coroutineScope {
            repeat(1_000_000) {
                launch(Dispatchers.Default) {
                    downloader.getUser(it)
                }
            }
        }
        assertEquals(1_000_000, downloader.downloaded().size)
    }
}