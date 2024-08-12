package coroutines.job

import kotlinx.coroutines.*

suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch(job) {
        delay(1000)
        println("Text 1")
    }
    launch(job) {
        delay(2000)
        println("Text 2")
    }
    // job.join() // Here we will await forever as only parent is completed
    // and we will be waiting for children to complete

    // job.complete() wont work as well as its not calling the children to complete

    job.children.forEach { it.join() } // only way to complete all the children +
    println("Will not be printed")
}


// better approach

class SomeService {
    private var jobs: List<Job> = emptyList()
    private val scope = CoroutineScope(SupervisorJob())
    fun startTask() {
        jobs += scope.launch {
// ...
        }
    }
    fun cancelTask() {
        jobs.forEach { it.cancel() }
    }
}