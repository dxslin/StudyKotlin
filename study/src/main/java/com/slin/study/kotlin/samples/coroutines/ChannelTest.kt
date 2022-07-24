package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.ref.SoftReference
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * author: slin
 * date: 2020/4/23
 * description:通道（生产者——消费者模式）
 * 1. `Channel`与`BlockingQueue`相似，不同的是`Channel`提供了挂起的`send`和`receive`替代了`BlockingQueue`的阻塞的`put`和`take`
 * 2. 扇出：多个协程监听同一个通道数据；扇入：多个协程发送数据到同一个通道
 * 3. 带缓冲的通道：与`BlockingQueue`一样，在`Channel`构造函数中可以指定缓冲大小，如果缓冲区满了就会被挂起直到数据被接收
 * 4. 通道是公平的，它遵循先进先出原则，公平调用每一个协程
 * 5. 计时器通道(ticker)，分为固定周期（FIXED_PERIOD）和固定延时（FIXED_DELAY），到时间后会发送一个`Unit`
 */

fun main() {
//    blockingQueueTest()
//    normalChannelTest()
//    channelCloseTest()
//    produceTest()
//    primeNumberTest()
//    fanOutTest()
//    fanInTest()
//    bufferChannelTest()
//    fairChannelTest()
//    tickerChannelTest()
//    actorTest()
    channelAsFlow()
//    channelCancelTest()
//    splitCallback()
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
        channel.close() // 结束发送
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
    coroutineContext.cancelChildren() // 关闭所有子协程来让主协程结束
}

private fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var num = start
    while (true) {
//        log("send number: $num")
        send(num++)
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

/**
 * 使用管道寻找素数
 * 原理：每次都通过`primeNumberFilter`启动一个新的通道监听自然数的产生，并过滤可以整除素数的自然数，
 *      主协程监听最后一个协程产生数字就是素数
 */
fun primeNumberTest() = runBlocking {
    var cur = numbersFrom(2)
    repeat(6) {
        val prime = cur.receive()
        log("prime number: $prime")
        cur = primeNumberFilter(cur, prime)
    }
    coroutineContext.cancelChildren()
}

/**
 * 扇出：多个协程监听同一个通道数据
 *
 */
fun fanOutTest() = runBlocking {
    println("fan out")
    val producer = produce<Int> {
        var num = 1
        while (true) {
            send(num++)
            delay(100)
        }
    }
    repeat(5) {
        launch {
            for (msg in producer) {
                log("Processor $it received $msg")
            }
        }
    }
    delay(950)
    producer.cancel()
}

/**
 * 扇入：多个协程发送数据到同一个通道
 *
 */
fun fanInTest() = runBlocking {
    println("fan in")
    val channel = Channel<String>()
    launch {
        while (true) {
            log("channel send apple")
            channel.send("apple")
            delay(200)
        }
    }
    launch {
        while (true) {
            log("channel send pear")
            channel.send("pear")
            delay(500)
        }
    }

    repeat(6) {
        println("receive: ${channel.receive()}")
    }
    coroutineContext.cancelChildren()
}

/**
 * 带缓冲的通道
 * 与`BlockingQueue`一样，在`Channel`构造函数中可以指定缓冲大小，如果缓冲区满了就会被挂起直到数据被接收
 */
fun bufferChannelTest() = runBlocking {
    // 指定通道缓存大小，当达到该大小时挂起
    val channel = Channel<Int>(4)
    val sender = launch {
        repeat(10) {
            // 这里只会发送四次，在尝试发送第五次的时候会被挂起
            log("sending $it")
            channel.send(it)
        }
    }
    // 等待一会儿，通道发送数据
    delay(1000)
    sender.cancel()
}

data class Ball(var hits: Int)

suspend fun player(name: String, table: Channel<Ball>) {
    for (ball in table) {
        ball.hits++
        log("$name  ${ball.hits}")
        delay(100)
        table.send(ball)
    }
}

/**
 * 通道是公平的，它遵循先进先出原则，公平调用每一个协程
 */
fun fairChannelTest() = runBlocking {
    val table = Channel<Ball>()
    // 先启动ping，然后启动pong，可以看到每次调用都是ping/pong互相交叉调用
    launch { player("ping", table) }
    launch { player("pong", table) }
    table.send(Ball(0))

    delay(1000)
    coroutineContext.cancelChildren() // 游戏结束
}

/**
 * 计时器通道(ticker)
 * 计时器通道分为固定周期（FIXED_PERIOD）和固定延时（FIXED_DELAY），到时间后会发送一个`Unit`
 */
fun tickerChannelTest() = runBlocking {
    val tickerChannel = ticker(100, 0) // 创建计时器通道

    // 初始化，尚未经过延迟
    var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    log("Initial element is available immediately: $nextElement")

    // 所有随后到来的元素都经过了100ms，这里没有等待到100ms，所以为null
    nextElement = withTimeoutOrNull(50) { tickerChannel.receive() }
    log("Next element is not ready in 50ms: $nextElement")

    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    log("Next element is read in 100ms: $nextElement")

    // 模拟大量消费延迟
    println("Consumer pauses for 150ms")
    delay(150)

    // 下一个元素立即可用
    nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
    log("Next element is available immediately after large consumer delay: $nextElement")

    // 请注意，`receive` 调用之间的暂停被考虑在内，下一个元素的到达速度更快
    nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
    log("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

    tickerChannel.cancel()
}

/**
 * actor：启动一个协程接收通道发送的数据
 * send 会等待发送完毕再发送下一个
 * offer/trySend 发送消息是发现通道接收数据未结束便会抛弃数据，可以用来做防止快速点击
 * 这个api会逐渐被抛弃，不建议使用
 */
fun actorTest() = runBlocking {
    val c = actor<String> {
        for (msg in channel) {
            delay(1000)
            log("actor receive: $msg")
        }
    }
    repeat(10) {
        c.send("send $it")
        log("send $it")
    }

    delay(3000)

    repeat(10) {
        c.trySend("offer $it")
        log("offer $it")
    }

    c.close()
}

/**
 * sample 固定采样时间，在固定的时间窗口里面取最后一次数据
 * debounce 防抖动，如果两次数据间隔时间少于设定时间则抛弃上一次的数据
 */
fun channelAsFlow() = runBlocking {
    val channel = Channel<Int>()
    launch {
        repeat(10) {
            val random = Random.nextLong(1000)
//            val random = 300L
            delay(random)
            channel.trySend(it)
            log("channel send: $it  delay time: $random")
        }
    }

    launch {
        log("channelAsFlow receive start")
        channel.consumeAsFlow()
//            .sample(500)
            .debounce(500)
            .collect {
                log("collect: $it current time: ${System.currentTimeMillis()}")
            }

        log("channelAsFlow receive end")
    }
}

fun channelCancelTest() = runBlocking {
    val channel = Channel<Int>(onBufferOverflow = BufferOverflow.SUSPEND)
    var data = 1
    fun sendData() {
        launch {
            delay(1000)
            log("send data $data")
//            channel.offer(data++)
//                channel.send(data++)
            channel.trySendBlocking(data++)
//                .onFailure { log("send failure") }
//                .onSuccess { log("send success") }
            log("send data end")
        }
    }

    sendData()
    val job = launch {
        try {
            log("receive-1: ${channel.receive()}")
            log("cccc")
        } catch (e: CancellationException) {
            //            e.printStackTrace()
            log("cancel receive-1: ${channel.receive()}")
        }
    }

    delay(100)
    job.cancel()
    sendData()
    delay(200)
    launch {
        log("receive-2: ${channel.receive()}")
        log("dddd")
    }
    log("eeee")

    delay(10000)

    sendData()
    delay(200)
    launch {
        log("receive-3: ${channel.receive()}")
        log("dddd")
    }
    log("ffff")
}

interface MultiCallback {
    fun call1(value: Int)
    fun call2(value: Int)
}

@Volatile
var data: Int = 1
var multiCallback: MultiCallback? = null

private fun produceData1(index: Int) {
    thread {
        log("produceData1-$index: $data")
        Thread.sleep(1000)
        log("produceData1-$index send-1: $data")
        multiCallback?.call1(data++)
    }
}

private fun produceData2() {
    thread {
        Thread.sleep(2000)
        log("send-2: $data")
        multiCallback?.call1(data++)
    }
}

interface Callback<T> {
    fun callback(value: T)
}

class SoftRefCallback<T>() : Callback<T> {
    private val callbacks: ArrayDeque<SoftReference<Callback<T>>> = ArrayDeque()
    override fun callback(value: T) {
        synchronized(this) {
            callbacks.removeFirst()
        }.get()?.callback(value)
    }

    private fun indexOf(callback: Callback<T>): Int {
        var result = -1
        callbacks.forEachIndexed { index, softReference ->
            if (softReference.get() == callback) {
                result = index
                return@forEachIndexed
            }
        }
        return result
    }

    fun registerCallback(callback: Callback<T>) {
        synchronized(this) {
            callbacks.addLast(SoftReference(callback))
        }
    }

    fun unregisterCallback(callback: Callback<T>) {
        synchronized(this) {
            val index = indexOf(callback)
            if (index >= 0) {
                callbacks[index].clear()
            }
        }
    }
}

class SplitCallback(
    @Volatile var callback1: SoftRefCallback<Int>? = null,
    @Volatile var callback2: SoftRefCallback<Int>? = null
) : MultiCallback {
    override fun call1(value: Int) {
        callback1?.callback(value)
    }

    override fun call2(value: Int) {
        callback2?.callback(value)
    }
}

fun splitCallback() {
    val mutex = Mutex()
    val channel = Channel<Int>()
    val splitCallback = SplitCallback(
        SoftRefCallback(),
        null
    )
    multiCallback = splitCallback
    runBlocking {
        val job = launch {
            val data = callbackFlow<Int> {
                log("flow 1")
                mutex.withLock {
                    log("flow 1 lock")
                    val callback: Callback<Int> = object : Callback<Int> {
                        override fun callback(value: Int) {
                            log("callback 1 $value")
                            trySendBlocking(value)
                            close()
                            splitCallback.callback1?.unregisterCallback(this)
                        }
                    }
                    splitCallback.callback1?.registerCallback(callback)
                    produceData1(1)
                    awaitClose {
                        splitCallback.callback1?.unregisterCallback(callback)
                    }
                    log("flow 1 unlock")
                }
            }.first()
            log("receive-1: $data")
        }
        delay(1100)
        launch {
            val data = callbackFlow<Int> {
                log("flow 2")
                mutex.withLock {
                    log("flow 2 lock")
                    val callback: Callback<Int> = object : Callback<Int> {
                        override fun callback(value: Int) {
                            log("callback 2 $value")
                            trySendBlocking(value)
                            close()
                            splitCallback.callback1?.unregisterCallback(this)
                        }
                    }
                    splitCallback.callback1?.registerCallback(callback)
                    produceData1(2)
                    awaitClose {
                        splitCallback.callback1?.unregisterCallback(callback)
                    }
                    log("flow 2 unlock")
                }
            }.first()
            log("receive-2: $data")
        }

        delay(50)
        job.cancel()
        delay(1200)
        delay(5000)
    }
}
