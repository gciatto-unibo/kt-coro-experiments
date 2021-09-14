package it.unibo.coro


import it.unibo.kt.test.asyncTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFlowUtils {

    private val random = Random(0)

    @Test
    fun testFlowToSequence() {
        val seq = flowOf(1, 2, 3).toSequence()

        sequenceOf(1, 2, 3).zip(seq).forEach { (x, y) ->
            assertEquals(x, y)
        }
    }

    suspend fun <T> delayed(x: T): T {
        println(x)
        delay(random.nextLong(1000))
        return x
    }

    @Test
    fun testLongFlowToSequence1() = asyncTest {
        val x = channelFlow {
            for (i in 1..100) {
                launch {
                    send(delayed(i))
                }
            }
        }
        assertEquals((1..100).toSet(), x.toSequence().toSet())
    }

    @Test
    fun testLongFlowToSequence2() = asyncTest {
        val x = flow {
            for (i in 1..10) {
                emit(i)
            }
        }.map { delayed(it) }
        assertEquals((1..10).toSet(), x.toSequence().toSet())
    }

    @Test
    fun testFlow() = asyncTest {
        assertEquals(3, flowOf(1, 2, 3).count())
    }
}