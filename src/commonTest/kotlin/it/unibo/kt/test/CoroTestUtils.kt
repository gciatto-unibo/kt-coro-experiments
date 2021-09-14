package it.unibo.kt.test

import kotlinx.coroutines.CoroutineScope

expect fun asyncTest(block: suspend CoroutineScope.() -> Unit)

expect val testCoroutineScope: CoroutineScope
