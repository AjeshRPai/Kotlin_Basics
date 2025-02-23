package rxjava

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.subjects.PublishSubject


fun main1() {
    // Create a PublishSubject
    val subject = PublishSubject.create<String>()

    // First subscriber subscribes
    subject.subscribe { item -> println("Subscriber 1 received: $item") }

    // Emit values before second subscriber joins
    subject.onNext("Item 1")
    subject.onNext("Item 2")

    // Second subscriber subscribes
    subject.subscribe { item -> println("Subscriber 2 received: $item") }

    // Emit more values
    subject.onNext("Item 3")
    subject.onNext("Item 4")

    // Complete the subject
    subject.onComplete()
}


fun main() {
    val subject = PublishSubject.create<String>()

    // First subscriber subscribes
    subject.subscribe(
        { item -> println("Subscriber 1 received: $item") },
        { error -> println("Subscriber 1 received error: ${error.message}") },
        { println("Subscriber 1 completed") }
    )

    // Emit values
    subject.onNext("Item 1")
    subject.onNext("Item 2")

    // Introduce an error
    subject.onError(RuntimeException("Something went wrong!"))

    // Second subscriber subscribes after error
    subject.subscribe(
        { item -> println("Subscriber 2 received: $item") },
        { error -> println("Subscriber 2 received error: ${error.message}") },
        { println("Subscriber 2 completed") }
    )

    // Emit more values (these will NOT be received because the subject has terminated)
    subject.onNext("Item 3")
}
