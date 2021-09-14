package it.unibo.coro

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

actual fun <T> Flow<T>.toSequence(coroutineScope: CoroutineScope): Sequence<T> {
    val queue = mutableListOf<Any?>()
    coroutineScope.async {
        collect {
            queue.add(it)
        }
        queue.add(PoisonPill)
    }
    return sequence {
        while (true) {
            if (queue.isNotEmpty()) {
                val i = queue.listIterator()
                while (i.hasNext()) {
                    val current = i.next()
                    i.remove()
                    yield(current)
                }
            }
        }
    }.takeWhile { it != PoisonPill }.map { it.unsafeCast<T>() }
}

internal actual val backgroundScope: CoroutineScope
    get() = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)