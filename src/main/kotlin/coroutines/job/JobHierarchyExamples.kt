package coroutines.job

import kotlinx.coroutines.*

 fun main(){
    runBlocking {
        val name = CoroutineName("Some name")
        val job = Job()
        launch {
            delay(1000)
            println("without inheriting job") // will run
        }

        launch(name + job) {
            println("print name + job executing before delay")
            delay(2000)
            println("print name + job executing")
        // runBlocking wont wait for this to finish as its not aware of this job
        }

        launch(Job()) {
            println("print new job executing before delay")
            delay(3000)
            println("print new job executing")
        // wont run as runBlocking is not aware of this job
        }

        job.join()
        job.complete()
        // to run the second

    }
}