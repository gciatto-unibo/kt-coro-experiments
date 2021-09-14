package it.unibo.kt.test

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

actual val testCoroutineScope: CoroutineScope =
    CoroutineScope(SupervisorJob() + Executors.newSingleThreadExecutor().asCoroutineDispatcher())

actual fun asyncTest(block: suspend CoroutineScope.() -> Unit): Unit =
    runBlocking(testCoroutineScope.coroutineContext) {
        launch {
            block()
        }
    }