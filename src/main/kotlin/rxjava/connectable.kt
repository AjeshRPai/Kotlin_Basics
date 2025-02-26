package rxjava

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.flowables.ConnectableFlowable
import java.util.concurrent.TimeUnit

fun connectable() {
    val source = Flowable.interval(1, TimeUnit.SECONDS)
        .take(5)
        .publish() // Convert to ConnectableFlowable

    source.subscribe { println("Subscriber 1: $it") }
    Thread.sleep(2000) // Wait before another subscription
    source.subscribe { println("Subscriber 2: $it") }

    // Start emitting
    source.connect()
}

fun examplePublish() {
    val flowable: ConnectableFlowable<Long> = Flowable.interval(1, TimeUnit.SECONDS)
        .publish()

    flowable.subscribe { println("Subscriber 1: $it") }

    Thread.sleep(3000) // Let it emit 3 values

    flowable.connect() // Start emitting

    Thread.sleep(3000) // Another 3 values emitted

    flowable.subscribe { println("Subscriber 2: $it") }

    Thread.sleep(3000) // Both subscribers receive new values
}

fun exampleReplay() {
    val flowable: ConnectableFlowable<Long> = Flowable.interval(1, TimeUnit.SECONDS)
        .replay()

    flowable.connect() // Start emitting immediately

    Thread.sleep(3000) // Emit 3 values

    flowable.subscribe { println("Subscriber 1 (Replay): $it") } // Gets previous values

    Thread.sleep(3000)

    flowable.subscribe { println("Subscriber 2 (Replay): $it") } // Gets all past values too
}

fun exampleReset() {
    val flowable: ConnectableFlowable<Long> = Flowable.interval(1, TimeUnit.SECONDS)
        .replay()

    flowable.connect() // Start emitting

    Thread.sleep(3000) // Emit some values

    flowable.reset() // Clear replay buffer

    flowable.subscribe { println("Subscriber 1 (After Reset): $it") }

    Thread.sleep(3000)
}

fun main() {
    println("---- Publish Example ----")
    examplePublish()

    println("\n---- Replay Example ----")
    exampleReplay()

    println("\n---- Reset Example ----")
    exampleReset()
}
