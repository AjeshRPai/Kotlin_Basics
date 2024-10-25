package Function

class Lock {
    fun lock() {
        println("Lock acquired")
    }

    fun unlock() {
        println("Lock released")
    }
}

inline fun <T> withLock(lock: Lock, block: () -> T): T {
    lock.lock()
    try {
        return block()
    } finally {
        lock.unlock()
    }
}

fun main() {
    // example 1
    val isUserLoggedIn = true

    runIf(isUserLoggedIn) {
        println("Welcome, user!")
    }

    // example 2
    val lock = Lock()

    withLock(lock) {
        println("Critical section of code")
    }


    // kotlin collections inline
    // check each fun implemention
    val numbers = listOf(1, 2, 3, 4, 5)
    numbers.filter { it % 2 == 0 }
        .map { it * 3 }
        .forEach { println(it) }


    val example = SecureData()
    example.printSecretMessage()  // Inline function exposes `printSecretMessage` here


    // why use crossinline
    performAction {
        println("Inside action")
        return  // Non-local return, exits from `main`
    }
    println("This will not be printed")  // This will be skipped due to non-local return
}

inline fun runIf(condition: Boolean, block: () -> Unit) {
    if (condition) {
        block()
    }
}

inline fun performAction(action: () -> Unit) {
    println("Action started")
    action()
    println("Action completed")
}


// reveal secret
class SecureData {
    private fun getSecretKey(): String {
        return "SuperSecretKey123"
    }

    inline fun printSecretMessage() {
//        val secret = getSecretKey() // Calls the private method
        println("The secret key is: $")
    }
}



