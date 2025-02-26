package rxjava

import io.reactivex.rxjava3.processors.*

fun publishProcessorDemo() {
    println("\n--- PublishProcessor Demo ---")
    val processor = PublishProcessor.create<Int>()

    processor.subscribe { println("Subscriber 1 received: $it") }

    processor.onNext(1)
    processor.onNext(2)

    processor.subscribe { println("Subscriber 2 received: $it") } // Won’t receive 1 & 2

    processor.onNext(3)
}

fun behaviorProcessorDemo() {
    println("\n--- BehaviorProcessor Demo ---")
    val processor = BehaviorProcessor.createDefault(0) // Default value emitted

    processor.subscribe { println("Subscriber 1 received: $it") }

    processor.onNext(1)
    processor.onNext(2)

    processor.subscribe { println("Subscriber 2 received: $it") } // Gets latest value (2)

    processor.onNext(3)
}

fun replayProcessorDemo() {
    println("\n--- ReplayProcessor Demo ---")
    val processor = ReplayProcessor.create<Int>()

    processor.onNext(1)
    processor.onNext(2)

    processor.subscribe { println("Subscriber 1 received: $it") } // Receives all past values

    processor.onNext(3)
}

fun asyncProcessorDemo() {
    println("\n--- AsyncProcessor Demo ---")
    val processor = AsyncProcessor.create<Int>()

    processor.onNext(1)
    processor.onNext(2)
    processor.onNext(3)

    processor.subscribe { println("Subscriber 1 received: $it") } // Won't get anything yet

    processor.onComplete() // Emits only the last value (3)

    processor.subscribe { println("Subscriber 2 received: $it") } // Gets only 3
}

fun main() {
    publishProcessorDemo()
    behaviorProcessorDemo()
    replayProcessorDemo()
    asyncProcessorDemo()
}
