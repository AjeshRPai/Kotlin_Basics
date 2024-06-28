package Coroutines.exercises

import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShowUserDataUseCaseTest {
    @Test
    fun `should show data on view`() = runTest {
        // given
        val repo = FakeUserDataRepository()
        val view = FakeUserDataView()
        val useCase = ShowUserDataUseCase(repo, view, backgroundScope)

        // when
        useCase.show()

        // then
        assertEquals(
            listOf(User("Ben", listOf(Friend("some-friend-id-1")), Profile("Example description"))),
            view.printed
        )
    }

    @Test
    fun `should load user data asynchronously and not wait for notify`() = runTest {
        // given
        val repo = FakeUserDataRepository()
        val view = FakeUserDataView()
        val useCase = ShowUserDataUseCase(repo, view, backgroundScope)

        // when
        useCase.show()

        // then
        assertEquals(1, view.printed.size)
        assertEquals(FETCH_TIMEOUT, currentTime)
    }

    @Test
    fun `should start notify profile shown after `() = runTest {
        // given
        val repo = FakeUserDataRepository()
        val view = FakeUserDataView()
        val notificationScope = backgroundScope
        val useCase = ShowUserDataUseCase(repo, view, notificationScope)

        // when
        useCase.show()

        // then
        notificationScope.coroutineContext.job.children.forEach { it.join() }
        assertEquals(FETCH_TIMEOUT + NOTIFY_TIMEOUT, currentTime)
        assertTrue(repo.notifyCalled)
    }

    class FakeUserDataRepository : UserDataRepository {
        var notifyCalled = false

        override suspend fun notifyProfileShown() {
            delay(NOTIFY_TIMEOUT)
            notifyCalled = true
        }

        override suspend fun getName(): String {
            delay(FETCH_TIMEOUT)
            return "Ben"
        }

        override suspend fun getFriends(): List<Friend> {
            delay(FETCH_TIMEOUT)
            return listOf(Friend("some-friend-id-1"))
        }

        override suspend fun getProfile(): Profile {
            delay(FETCH_TIMEOUT)
            return Profile("Example description")
        }
    }

    class FakeUserDataView : UserDataView {
        var printed = listOf<User>()

        override fun show(user: User) {
            printed = printed + user
        }
    }

    companion object {
        const val FETCH_TIMEOUT = 1234L
        const val NOTIFY_TIMEOUT = 2345L
    }
}