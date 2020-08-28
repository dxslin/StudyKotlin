package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis


/**
 * author: slin
 * date: 2020/4/28
 * description:共享可变状态与并发
 * 下面示例介绍了几种在协程并发中同步共享数据的方式
 */

fun main() {
    shareTest()
    actorCounterTest()
}


suspend fun massiveRun(action: suspend () -> Unit) {
    val count = 100 //启动协程的数量
    val times = 1000    //每个协程执行action的次数
    val time = measureTimeMillis {
        coroutineScope {
            repeat(count) {
                launch {
                    repeat(times) { action() }
                }
            }
        }
    }
    println("Completed ${count * times} actions in $time ms")
}

@Volatile
var counter2 = 0

/**
 * 协程的并发与数据同步
 */
fun shareTest() = runBlocking {
    // 协程使用多线程调度器`Dispatchers.Default`并发执行，数据没有他同步，存在并发问题 counter1 != 100 * 1000
    var counter1 = 0
    withContext(Dispatchers.Default) {
        massiveRun { counter1++ }
    }
    println("Normal--Counter1: $counter1\n")

    // 即使在共享变量上面加上`volatile `也无济于事，因为`volatile `保证读取和写入线性化，但是递增不行
    //这里文档上说是运行得更慢了，但是我却运行得更快
    withContext(Dispatchers.Default) {
        massiveRun { counter2++ }
    }
    println("Volatile--Counter2: $counter2 \n")

    // Java的解决办法，使用线程安全的数据结构`AtomicInteger `，测试发现这种方式得到的结果是最快的
    val counter3 = AtomicInteger(0)
    withContext(Dispatchers.Default) {
        massiveRun { counter3.incrementAndGet() }
    }
    println("AtomicInteger--Counter3: ${counter3.get()}\n")

    val counterContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    //将变量访问限制在一个线程中，这里会不停的切换default线程和singleThread，所以执行的很慢
    var counter4 = 0
    withContext(Dispatchers.Default) {
        massiveRun {
            withContext(counterContext) { counter4++ }
        }
    }
    counterContext.close()
    println("counterContext1--Counter4: $counter4\n")

    //将所有协程都限制在一个线程中，这种方式速度比上面那种方式少了大量切换线程的时间，所以速度更快
    var counter5 = 0
    withContext(counterContext) {
        massiveRun { counter5++ }
    }
    counterContext.close()
    println("counterContext2--Counter5: $counter5\n")

    //加锁，使用互斥来保证共享状态的所有修改，协程中替代线程中的锁`synchronized`和`ReentrantLock`的是`Mutex`，
    //  它是挂起执行，而不是阻塞线程
    val mutex = Mutex()
    var counter6 = 0
    withContext(Dispatchers.Default) {
        massiveRun {
            mutex.withLock { counter6++ }
        }
    }
    println("mutex--Counter6: $counter6\n")

}

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

/**
 * 使用`actor`发送消息来计数，这种方式与上下文无关紧要，`actor`本身是一个协程，是按顺序执行的，
 */
fun actorCounterTest() = runBlocking {
    val actor = actor<CounterMsg> {
        var counter = 0
        for (msg in channel) {
            when (msg) {
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter)
            }
        }
    }

    withContext(Dispatchers.Default) {
        massiveRun { actor.send(IncCounter) }
    }
    val response = CompletableDeferred<Int>()
    actor.send(GetCounter(response))
    println("actor--Counter = ${response.await()}")
    actor.close()


}

