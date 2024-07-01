package coroutines.exercises

import kotlinx.coroutines.*

class ShowUserDataUseCase(
    private val repo: UserDataRepository,
    private val view: UserDataView,
    private val notificationScope: CoroutineScope,
) {
    suspend fun show() {
        coroutineScope {
            val name = async { repo.getName() }
            val friends = async { repo.getFriends() }
            val profile = async { repo.getProfile() }
            view.show(User(name.await(), friends.await(), profile.await()))
        }
        notificationScope.launch {
            repo.notifyProfileShown()
        }
    }
}

interface UserDataRepository {
    suspend fun notifyProfileShown()
    suspend fun getName(): String
    suspend fun getFriends(): List<Friend>
    suspend fun getProfile(): Profile
}

interface UserDataView {
    fun show(user: User)
}

data class User(
    val name: String,
    val friends: List<Friend>,
    val profile: Profile
)
data class Friend(val id: String)
data class Profile(val description: String)

