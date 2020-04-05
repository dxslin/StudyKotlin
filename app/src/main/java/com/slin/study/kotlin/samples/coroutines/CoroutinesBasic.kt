package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

/**
 * author: slin
 * date: 2020-04-04
 * description: 协程基础
 * 1. 使用`launch`创建一个协程
 * 2. 使用`runBlocking`创建协程，与`launch`不同的是`runBlocking`里面`delay`会阻塞整个线程
 * 3. 使用`join`等待任务执行完毕
 * 4. `coroutineScope`创建一个协程作用域，其在所有已启动的子协程结束前不会结束，
 *      `runBlocking`和`coroutineScope`都会等待子协程执行完毕，但是`runBlocking`是阻塞当前线程，
 *      而`coroutineScope`是挂起协程，会继续跑其他协程体，是挂起函数
 * 5. 创建一个挂起函数，只需要使用`suspend`修饰函数即可
 * 6. 协程的运行效率比线程高得多
 */

fun main() {
//    launchTest()
//    runBlockingTest()
    joinTest()
//    coroutineScopeTest();
//
//    runBlocking {
//        launch {
//            doWorld()
//        }
//        println("Hello, ")
//    }
//
//    createMassCoroutines();
//    createMassThread();

}

/**
 * 使用`launch`创建一个协程
 */
fun launchTest() {
    GlobalScope.launch {
        delay(1000)
        println("Hello Coroutines")
    }
    println("Hello launchTest")
    //延时等待协程打印完毕
    Thread.sleep(2000)
    println("launchTest end\r\n")
}

/**
 * 使用`runBlocking`创建协程，与`launch`不同的是`runBlocking`里面`delay`会阻塞整个线程
 */
fun runBlockingTest() {
    GlobalScope.launch {
        delay(1000)
        println("Hello Coroutines")
    }
    println("Hello runBlockingTest")
    //runBlocking里面执行delay函数会阻塞整个线程
    runBlocking {
        delay(2000)
    }

    println("runBlockingTest end\r\n")
}

/**
 * 使用`join`等待任务执行完毕
 */
fun joinTest() = runBlocking {
    val job = launch {
        delay(500)
        println("Hello Coroutines")
    }
    println("Hello joinTest")
    //等待任务执行完毕
    job.join()

    println("joinTest end\r\n")
}

/**
 * `coroutineScope`创建一个协程作用域，其在所有已启动的子协程结束前不会结束
 * `runBlocking`和`coroutineScope`都会等待子协程执行完毕，但是`runBlocking`是阻塞当前线程，而`coroutineScope`是挂起协程，是挂起函数
 */
fun coroutineScopeTest() = runBlocking {
    launch {
        delay(200)
        println("Task form Blocking")
    }
    coroutineScope {
        launch {
            delay(500)
            println("Task from nested launch")
        }
        delay(100)
        println("Task from coroutine scope")
    }
    println("Coroutine scope is over\r\n")
}

/**
 * 创建一个挂起函数，只需要使用`suspend`修饰函数即可
 */
suspend fun doWorld() {
    delay(100)
    println("World!")
}

/**
 * 创建大量协程，这里创建10000个协程延时一秒输出值，所有协程执行完毕只用了1.4秒左右
 */
fun createMassCoroutines() {
    val start = System.currentTimeMillis();
    println("create coroutine start: $start")
    runBlocking {
        repeat(10000) {
            launch {
                delay(1000)
                print("$it ")
            }
        }
    }
    println("\r\ncoroutine run end, usage time: ${System.currentTimeMillis() - start} ${System.currentTimeMillis()}")
}

/**
 * 创建大量线程，这里创建10000个线程延时一秒输出值，所有线程执行完毕只用了6秒左右，可见协程比线程要快得多
 */
fun createMassThread() {
    val countThread = 10000;
    val countDownLatch = CountDownLatch(countThread)
    val start = System.currentTimeMillis();
    println("create thread start: $start")
    repeat(countThread) {
        thread {
            Thread.sleep(1000)
            print("$it ")
            countDownLatch.countDown()
        }
    }
    countDownLatch.await()
    println("\r\nthread run end, usage time: ${System.currentTimeMillis() - start} ${System.currentTimeMillis()}")
}
