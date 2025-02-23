import kotlinx.coroutines.*

private val dispatcher = Dispatchers.IO.limitedParallelism(1)

class CancellingRefresher(
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    suspend fun refresh() {
        job?.cancel()
        job = scope.launch {
            delay(1000) // Simulating refresh logic
            println("the message is printed")
        }
    }
}

suspend fun main() {
    sample3()
    delay(5000)
}


fun sample1() = runBlocking(Dispatchers.IO) {
    val currentThread = Thread.currentThread()
    println("currentThread: $currentThread")
    delay(3000) // a task that takes 3 seconds
    println("job completed")
}

//
fun sample2() = runBlocking(Dispatchers.Default) {
    val currentThread = Thread.currentThread()
    println("currentThread: $currentThread")
    delay(3000)
    println("job completed")
}

suspend fun sample3() = withContext(Dispatchers.IO){
    // current thread is the I/O thread, so the runBlocking will block the I/O thread.
    println("currentThread: ${Thread.currentThread()}")
    val result = runBlocking {
        println( "currentThread: ${Thread.currentThread()}") // current thread is the I/O thread
        delay(3000)
        println("job completed")
    }
    println( "Result: $result")
}