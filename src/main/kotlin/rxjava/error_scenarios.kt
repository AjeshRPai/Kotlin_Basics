package rxjava

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.functions.Supplier
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Ways to handle the error
 *
 * swallow the error and switch over to a backup Observable to continue the sequence
 * swallow the error and emit a default item
 * swallow the error and immediately try to restart the failed Observable
 * swallow the error and try to restart the failed Observable after some back-off interval
 *
 */


fun onError() {
    Observable.error<Exception>(IOException("Something went wrong"))
        .doOnError { error -> System.err.println("The error message is: " + error.message) }
        .subscribe(
            { x -> println("onNext should never be printed!") },
            { it.cause },
            { println("onComplete should never be printed!") })
}

// this is available for completable and may be
// helpful in handling
fun onErrorComplete() {
    Completable.fromAction {
        throw IOException()
    }.onErrorComplete { error: kotlin.Throwable? -> error is IOException }.subscribe(
        { println("IOException was ignored") },
        { error: kotlin.Throwable? -> System.err.println("onError should not be printed!") })
}


fun onErrorResumeNext() {
    val numbers = Observable.generate(
        Supplier { 1 },
        BiFunction { state: Int, emitter: Emitter<Int> ->
            emitter.onNext(state)
            state + 1
        })

    numbers.scan { x: Int?, y: Int? ->
        Math.multiplyExact(
            x!!, y!!
        )
    }.onErrorResumeNext { Observable.empty<Int>() }
        .subscribe(
            { x: Int? -> println(x) },
            { error: Throwable? -> System.err.println("onError should not be printed!") })
}

fun onRetry() {
    val source = Observable.interval(0, 1, TimeUnit.SECONDS)
        .flatMap { x: Long ->
            if (x >= 2) return@flatMap Observable.error<Long>(IOException("Something went wrong!"))
            else return@flatMap Observable.just<Long>(x)
        }

    source.retry { retryCount: Int, error: Throwable? -> retryCount < 3 }
        .blockingSubscribe(
            { x: Long -> println("onNext: $x") },
            { error: Throwable -> System.err.println("onError: " + error.message) })
}

fun retryWhen() {
    val source = Observable.interval(0, 1, TimeUnit.SECONDS)
        .flatMap { x: Long ->
            if (x >= 2) return@flatMap Observable.error<Long>(IOException("Something went wrong!"))
            else return@flatMap Observable.just<Long>(x)
        }

    source.retryWhen { errors: Observable<Throwable> ->
        errors.map { 1 } // Count the number of errors.
            .scan { x: Int?, y: Int? ->
                Math.addExact(
                    x!!, y!!
                )
            }
            .doOnNext { errorCount: Int -> println("No. of errors: $errorCount") } // Limit the maximum number of retries.
            .takeWhile { errorCount: Int -> errorCount < 3 } // Signal resubscribe event after some delay.
            .flatMapSingle<Any> { errorCount: Int -> Single.timer(errorCount.toLong(), TimeUnit.SECONDS) }
    }.blockingSubscribe(
        { x: Long -> println("onNext: $x") },
        { obj: Throwable -> obj.printStackTrace() },
        { println("onComplete") })
}


fun main() {
    retryWhen()
}