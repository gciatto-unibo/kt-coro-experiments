package it.unibo.kt.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise

actual val testCoroutineScope: CoroutineScope = MainScope()

actual fun asyncTest(block: suspend CoroutineScope.() -> Unit) {
    testCoroutineScope.promise {
        launch {
            this.block()
        }
    }
}
