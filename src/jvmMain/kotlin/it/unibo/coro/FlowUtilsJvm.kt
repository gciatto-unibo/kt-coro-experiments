package it.unibo.coro

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import java.util.concurrent.LinkedBlockingQueue

actual fun <T> Flow<T>.toSequence(coroutineScope: CoroutineScope): Sequence<T> {
    val queue = LinkedBlockingQueue<Any?>()
    coroutineScope.async {
        collect { queue.add(it) }
        queue.add(PoisonPill)
    }
    return sequence {
        while (true) {
            val current = queue.take()
            if (current == PoisonPill) {
                break
            } else {
                yield(current as T)
            }
        }
    }
}

internal actual val backgroundScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)