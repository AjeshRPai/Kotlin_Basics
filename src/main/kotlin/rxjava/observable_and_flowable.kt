package rxjava

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main() {
    println("Running Observable example...")
    runObservableExample()

    println("\nRunning Flowable example...")
    runFlowableExample()

    // Keep the program running to observe emissions
    Thread.sleep(5000)
}

// Simulating high-frequency sensor data using Observable
fun runObservableExample() {
    val observable = Observable.interval(1, TimeUnit.MILLISECONDS) // Emits every 1ms
        .map { it.toInt() }
        .observeOn(Schedulers.io())

    observable.subscribe(
        { println("Observable Received: $it") },
        { error -> println("Observable Error: ${error.message}") }
    )

    Thread.sleep(1000) // Allow time to observe emissions
}

// Simulating high-frequency sensor data using Flowable with backpressure handling
fun runFlowableExample() {
    val flowable = Flowable.interval(1, TimeUnit.MILLISECONDS) // Emits every 1ms
        .map { it.toInt() }
        .onBackpressureDrop() // Drops items if the subscriber is too slow
        .observeOn(Schedulers.io())

    flowable.subscribe(
        { println("Flowable Received: $it") },
        { error -> println("Flowable Error: ${error.message}") }
    )

    Thread.sleep(1000) // Allow time to observe emissions
}
