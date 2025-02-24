package rxjava

import io.reactivex.rxjava3.subjects.*

fun publishSubjectDemo() {
    val subject = PublishSubject.create<String>()
    println("[PublishSubject] Created")

    println("[PublishSubject] Subscriber 1 joins")
    subject.subscribe(
        { item -> println("[Subscriber 1] received: $item") },
        { error -> println("[Subscriber 1] received error: ${error.message}") },
        { println("[Subscriber 1] completed") }
    )

    subject.onNext("Item 1")
    subject.onNext("Item 2")

    println("[PublishSubject] Subscriber 2 joins")
    subject.subscribe(
        { item -> println("[Subscriber 2] received: $item") },
        { error -> println("[Subscriber 2] received error: ${error.message}") },
        { println("[Subscriber 2] completed") }
    )

    subject.onNext("Item 3")
    subject.onNext("Item 4")
    subject.onComplete()
    println("[PublishSubject] Emits values only to active subscribers, does not store past emissions.")
}

fun behaviorSubjectDemo() {
    val subject = BehaviorSubject.createDefault("Initial Value")
    println("[BehaviorSubject] Created with default value")

    println("[BehaviorSubject] Subscriber 1 joins")
    subject.subscribe { item -> println("[Subscriber 1] received: $item") }

    subject.onNext("Item 1")
    subject.onNext("Item 2")

    println("[BehaviorSubject] Subscriber 2 joins")
    subject.subscribe { item -> println("[Subscriber 2] received: $item") }

    subject.onNext("Item 3")
    subject.onComplete()
    println("[BehaviorSubject] Emits the latest value and all subsequent values to new subscribers.")
}

fun replaySubjectDemo() {
    val subject = ReplaySubject.create<String>()
    println("[ReplaySubject] Created")

    subject.onNext("Item 1")
    subject.onNext("Item 2")

    println("[ReplaySubject] Subscriber 1 joins")
    subject.subscribe { item -> println("[Subscriber 1] received: $item") }

    subject.onNext("Item 3")

    println("[ReplaySubject] Subscriber 2 joins")
    subject.subscribe { item -> println("[Subscriber 2] received: $item") }

    subject.onNext("Item 4")
    subject.onComplete()
    println("[ReplaySubject] Replays all emitted values to new subscribers.")
}

fun asyncSubjectDemo() {
    val subject = AsyncSubject.create<String>()
    println("[AsyncSubject] Created")

    subject.onNext("Item 1")
    subject.onNext("Item 2")
    subject.onNext("Item 3")

    println("[AsyncSubject] Subscriber 1 joins")
    subject.subscribe(
        { item -> println("[Subscriber 1] received: $item") },
        { error -> println("[Subscriber 1] error: ${error.message}") },
        { println("[Subscriber 1] completed") }
    )

    subject.onComplete()

    println("[AsyncSubject] Subscriber 2 joins after completion")
    subject.subscribe(
        { item -> println("[Subscriber 2] received: $item") },
        { error -> println("[Subscriber 2] error: ${error.message}") },
        { println("[Subscriber 2] completed") }
    )
    println("[AsyncSubject] Emits only the last value before completion.")
}

fun publishSubjectErrorDemo() {
    val subject = PublishSubject.create<String>()
    println("[PublishSubject with Error] Created")

    println("[PublishSubject with Error] Subscriber 1 joins")
    subject.subscribe(
        { item -> println("[Subscriber 1] received: $item") },
        { error -> println("[Subscriber 1] received error: ${error.message}") },
        { println("[Subscriber 1] completed") }
    )

    subject.onNext("Item 1")
    subject.onNext("Item 2")
    subject.onError(RuntimeException("Something went wrong!"))

    println("[PublishSubject with Error] Subscriber 2 joins after error")
    subject.subscribe(
        { item -> println("[Subscriber 2] received: $item") },
        { error -> println("[Subscriber 2] received error: ${error.message}") },
        { println("[Subscriber 2] completed") }
    )
    println("[PublishSubject with Error] Terminates upon error, preventing further emissions.")
}

fun main() {
    println("\n--- PublishSubject Example ---")
    publishSubjectDemo()

    println("\n--- BehaviorSubject Example ---")
    behaviorSubjectDemo()

    println("\n--- ReplaySubject Example ---")
    replaySubjectDemo()

    println("\n--- AsyncSubject Example ---")
    asyncSubjectDemo()

    println("\n--- PublishSubject with Error Example ---")
    publishSubjectErrorDemo()
}
