package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread


/**
 * author: slin
 * date: 2020/4/23
 * description:通道
 *
 */

fun main() {

//    blockingQueueTest()
//    normalChannelTest()
//    channelCloseTest()
//    produceTest()
    primeNumberTest()
}


fun blockingQueueTest() = runBlocking {
    val blockingQueue = LinkedBlockingQueue<Int>()
    thread {
        for (i in 1..5) {
            log("put $i")
            blockingQueue.put(i)
            Thread.sleep(1000)
        }
    }
    repeat(5) {
        println("take:${blockingQueue.take()}")
    }

    println("Done")
}

/**
 * `Channel`与`BlockingQueue`相似，
 * 不同的是`Channel`提供了挂起的`send`和`receive`替代了`BlockingQueue`的阻塞的`put`和`take`
 */
fun normalChannelTest() = runBlocking {
    val channel = Channel<Int>()
    launch {
        repeat(5) {
            channel.send(it)
        }
    }

    repeat(5) {
        log("receive: ${channel.receive()}")
    }
    println("Done")
}

/**
 * 关闭通道
 */
fun channelCloseTest() = runBlocking {
    val channel = Channel<Int>()
    launch {
        for (i in 1..5) {
            channel.send(i)
        }
        channel.close()     //结束发送
    }
    for (item in channel) {
        log("receive: $item")
    }
    println("Done")
}

/**
 * 生产者——消费者模式
 */
fun produceTest() = runBlocking {
    val sequence = produce {
        for (i in 1..5) {
            log("send: $i")
            send(i)
        }
    }
    sequence.consumeEach {
        log("consume: $it")
    }
    println("Done\n")


    val numbers = produce {
        var x = 1
        while (true) {
            send(x++)
        }
    }
    val squares = produce {
        for (x in numbers) {
            send(x * x)
        }
    }

    repeat(5) {
        log("receive:  ${squares.receive()}")
    }
    println("Done\n")
    coroutineContext.cancelChildren()   //关闭所有子协程来让主协程结束
}

private fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var num = start
    while (true) {
//        log("send number: $num")
        send(num++)
        delay(100)
    }
}

private fun CoroutineScope.primeNumberFilter(numbers: ReceiveChannel<Int>, prime: Int) =
    produce<Int> {
        for (x in numbers) {
            log("for  numbers: $x % $prime")
            if (x % prime != 0) {
                log("send filter number: $x")
                send(x)
            }
        }
    }

fun primeNumberTest() = runBlocking {
    var cur = numbersFrom(2)
    repeat(10) {
        val prime = cur.receive()
        log("prime number: $prime")
        cur = primeNumberFilter(cur, prime)
    }
    coroutineContext.cancelChildren()
}



