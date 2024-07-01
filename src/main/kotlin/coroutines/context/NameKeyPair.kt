package coroutines.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


// What makes CoroutineContext truly useful is its ability to merge two contexts
//together. When two elements with different keys are added, the resulting context
//responds to both keys.

fun main() {
    val ctx1: CoroutineContext = CoroutineName("Name1")
    println(ctx1[CoroutineName]?.name) // Name1
    println(ctx1[Job]?.isActive) // null
    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name) // null
    println(ctx2[Job]?.isActive) // true, because "Active"
// is the default state of a job created this way
    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name) // Name1
    println(ctx3[Job]?.isActive)
}

// Just like in a map, the new element replaces the previous one when another
//element with the same key is added.
//fun main() {
//    val ctx1: CoroutineContext = CoroutineName("Name1")
//    println(ctx1[CoroutineName]?.name) // Name1
//    val ctx2: CoroutineContext = CoroutineName("Name2")
//    println(ctx2[CoroutineName]?.name) // Name2
//    val ctx3 = ctx1 + ctx2
//    println(ctx3[CoroutineName]?.name) // Name2
//}