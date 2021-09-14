package it.unibo.coro

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

internal object PoisonPill

internal expect val backgroundScope: CoroutineScope

internal expect fun <T> Flow<T>.toSequence(coroutineScope: CoroutineScope = backgroundScope): Sequence<T>
