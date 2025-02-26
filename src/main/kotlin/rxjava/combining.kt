package rxjava

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit



fun startWith() {
    val names: Observable<String> = Observable.just("Spock", "McCoy")
    names.startWith(Observable.just("Kirk")).subscribe { item -> System.out.println(item) }
}

fun merge() {
    Observable.just(1, 2, 3)
        .mergeWith(Observable.just(4, 5, 6))
        .subscribe { item: Int? -> println(item) }
}

fun mergeDelayError() {
    val observable1 = Observable.error<String>(IllegalArgumentException(""))
    val observable2 = Observable.just("Four", "Five", "Six")
    Observable.mergeDelayError(observable1, observable2)
        .onErrorResumeNext { Observable.just("something else") }
        .doOnError { println(it) }
        .subscribe { item: String? -> println(item) }

}

fun combineLatest() {
    val newsRefreshes = Observable.interval(100, TimeUnit.MILLISECONDS)
    val weatherRefreshes = Observable.interval(50, TimeUnit.MILLISECONDS)
    Observable.combineLatest(
        newsRefreshes, weatherRefreshes
    ) { newsRefreshTimes: Long, weatherRefreshTimes: Long -> "Refreshed news $newsRefreshTimes times and weather $weatherRefreshTimes" }
        .subscribe { item: String? -> println(item) }

    Thread.sleep(5000)
}


fun main() {
    combineLatest()
}