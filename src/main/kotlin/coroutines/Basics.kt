package coroutines

import kotlinx.coroutines.suspendCancellableCoroutine


suspend fun main() {
    val i: Int = suspendCancellableCoroutine<Int> { c ->
        c.resumeWith(Result.success(42))
    }
    println(i) // 42
    val str: String = suspendCancellableCoroutine<String> { c -> c.resumeWith(Result.success("Some text")) }
    println(str) // Some text
    val b: Boolean = suspendCancellableCoroutine<Boolean> { c -> c.resumeWith(Result.success(true))
    }
    println(b) // true
}