package com.slin.study.kotlin.samples.coroutines

import com.slin.study.kotlin.samples.coroutines.SelectTest.testSelectOther
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select
import kotlin.random.Random


/**
 * author: slin
 * date: 2020/8/13
 * description: Select表达式
 * Select表达式可以同时等待多个挂起函数，并选择第一个可用的
 *
 */

fun main() {
//    testSelectOnReceive()
//    testSelectOnReceiveNull()
//    testSelectOnSend()
//    testSelectOnAwait()
    testSelectOther()
}

@OptIn(ExperimentalCoroutinesApi::class)
private object SelectTest {

    /**
     * 测试Select
     * 不同于receive，select表达式使用onReceive可以同时从两个通道接收数据
     */
    fun testSelectOnReceive() = runBlocking {
        val fizz = produce {
            repeat(10) {
                delay(300)
                send(it)
                log("fizz send $it")
            }
        }
        val buzz = produce {
            repeat(10) {
                delay(500)
                send(it)
                log("buzz send $it")
            }
        }

        repeat(5) { times ->
            select<Unit> {
                fizz.onReceive { value ->
                    log("$times: fizz -> $value")
                }
                buzz.onReceive { value ->
                    log("$times: buzz -> $value")
                }
            }
        }

        coroutineContext.cancelChildren()
    }

    /**
     * onReceive 在已经关闭的通道执行会失败，并抛出异常[kotlinx.coroutines.channels.ClosedReceiveChannelException]
     * onReceiveOrNull 在通道关闭时会收到 null ，且第一个优先收到，如果第一个通道关闭，其他通道发送数据，那么仅第一个会收到null，已废弃
     * onReceiveCatching 会返回通道执行结果和失败原因，且第一个优先收到，如果第一个关闭，其他通道发送数据，那么仅第一个会收到结果
     */
    fun testSelectOnReceiveNull() = runBlocking {
        val fizz = produce {
            repeat(4) {
                send(it)
            }
        }
        val buzz = produce {
            repeat(5) {
                send(it)
            }
        }
        repeat(10) { times ->
            select {
                val fizzSelectClause1 = fizz.onReceiveCatching
                fizzSelectClause1 { value ->
                    if (value.isSuccess) {
                        log("$times: fizz value = $value")
                    } else {
                        log("$times: Channel fizz: close: ${value.isClosed}  fail: ${value.isFailure}  ${value.exceptionOrNull()}")
                    }
                }

                val buzzSelectClause1 = buzz.onReceiveCatching
                buzzSelectClause1 { value ->
                    if (value.isSuccess) {
                        log("$times: buzz value = $value")
                    } else {
                        log("$times: Channel buzz: close: ${value.isClosed}  fail: ${value.isFailure}  ${value.exceptionOrNull()}")
                    }
                }

            }
        }
    }

    /**
     * onSend 可以很好的与选择的偏向特性结合使用
     * 比如下面例子，当主通道上面的消费者无法跟上生产者速度时，发送到备用的 slide 通道上面
     */
    fun testSelectOnSend() = runBlocking {
        val slide = Channel<Int>()

        val producer = produce<Int> {
            for (i in 1..10) {
                delay(100)
                select<Unit> {
                    onSend(i) {}        //发送到主通道
                    slide.onSend(i) {}  //发送到备用通道，如果主通道正在运行，那么就会发送到此通道
                }
            }
        }

        launch {
            //创建一个快速消费者
            slide.consumeEach {
                log("Slide channel has $it")
            }
        }
        //模拟耗时消费
        producer.consumeEach {
            log("Consuming $it")
            delay(250)
        }
        log("Done consuming")
        coroutineContext.cancelChildren()
    }

    /**
     * onAwait 等待异步协程执行完毕
     */
    fun testSelectOnAwait() = runBlocking {
        val random = Random(10)
        val list = List(1) {
            async {
                val times = random.nextLong(1000)
                log("async-$it wait 1 start $times ms")
                delay(times)
                log("async-$it wait 1 end $times ms")
                log("async-$it wait 2 start $times ms")
                delay(times)
                log("async-$it wait 2 end $times ms")
                "Wait for $times ms"
            }
        }

        val result = select<String> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    log("onWait: '$answer'")
                    "Deferred $index produced answer '$answer'"
                }
            }
        }
        log(result)
        val countActive = list.count { it.isActive }
        log("$countActive coroutines are still active")
    }


    private fun CoroutineScope.switchMapDeferreds(input: ReceiveChannel<Deferred<String>>) =
        produce<String> {
            var current = input.receive() // start with first received deferred value
            log("input receive '$current'")
            while (isActive) { // loop while not cancelled/closed
                val next =
                    select<Deferred<String>?> { // return next deferred value from this select or null
                        input.onReceive { update ->
                            log("onReceiveOrNull update '$update'")
                            update // replaces next value to wait
                        }
                        current.onAwait { value ->
                            send(value) // send value that current deferred has produced
                            log("onAwait send '$value'")
                            input.receiveCatching()
                                .getOrNull() // and use the next deferred from the input channel
                        }
                    }
                if (next == null) {
                    log("Channel was closed")
                    break // out of loop
                } else {
                    current = next
                }
            }
        }

    private fun CoroutineScope.stringAsync(str: String, time: Long) = async {
        log("asyncString delay start str = $str time = $time")
        delay(time)
        log("asyncString str = $str time = $time")
        str
    }

    fun testSelectOther() = runBlocking {
        val chan = Channel<Deferred<String>>() // the channel for test
        launch { // launch printing coroutine
            for (s in switchMapDeferreds(chan))
                log(s) // print each received string
        }
        log("sending start")
        chan.send(stringAsync("BEGIN", 100))
        delay(200) // enough time for "BEGIN" to be produced
        chan.send(stringAsync("Slow", 500))
        delay(100) // not enough time to produce slow
        chan.send(stringAsync("Replace", 100))
        delay(500) // give it time before the last one
        chan.send(stringAsync("END", 500))
        delay(1000) // give it time to process
        chan.close() // close the channel ...
        delay(500) // and wait some time to let it finish
    }

}

