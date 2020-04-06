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
 * 7. 使用`cancel`取消协程，只能取消挂起函数，计算任务是无法取消的，可以通过`isAlive`判断任务是否被取消了
 */

fun main() {
//    launchTest()
//    runBlockingTest()
//    joinTest()
//    coroutineScopeTest()
//
//    runBlocking {
//        launch {
//            doWorld()
//        }
//        println("Hello, ")
//    }
//
//    createMassCoroutines()
//    createMassThread()
//    cancelJobTest()
//    cancelJobTest2()
//    cancelFinallyTest()
    timeOutTest()

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
    val start = System.currentTimeMillis()
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
    val countThread = 10000
    val countDownLatch = CountDownLatch(countThread)
    val start = System.currentTimeMillis()
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

/**
 * 取消任务, cancel可以取消协程
 * 任务取消是
 */
fun cancelJobTest() = runBlocking {
    val job = launch {
        repeat(1000) {
            println("job： I'm sleeping $it ...")
            delay(500)
        }
    }
    delay(1300)
    println("main: I'm tried to waiting")
    job.cancel()
    job.join()
    println("main: Now I can quit")
}

/**
 * 计算任务是无法取消的，挂起函数都是可以取消的，感觉挂起函数就是最小挂起单元
 * 我们可以显示的调用`isActive`来判断是否已经被取消了
 */
fun cancelJobTest2() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) {
//        while (i <= 5){
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500
            }
        }
    }
    delay(1300)
    println("main: I'm tried to waiting")
    //cancel和join合并
    job.cancelAndJoin()
    println("main: Now I can quit")
}

/**
 * 任务取消会抛出` CancellationException`异常，可以通过`try{...}finally{...}`来回收资源
 * 在`finally`里面调用挂起函数都会直接抛出` CancellationException`，需要使用`withContext(NonCancellable)`包裹
 */
fun cancelFinallyTest() = runBlocking {
    val job = launch {
        try {
            repeat(100) {
                println("job： I'm sleeping $it ...")
                delay(500)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }

    delay(1300)
    println("main: I'm tried to waiting")
    job.cancelAndJoin()
    println("main: Now I can quit")
}

/**
 * `withTimeout`超时控制，超时后会抛出`TimeoutCancellationException`异常
 * `withTimeoutOrNull`通过返回`null`来表示超时，不会抛出异常
 */
fun timeOutTest() = runBlocking {
//    withTimeout(1300){
//        launch {
//            repeat(100){
//                println("job： I'm sleeping $it ...")
//                delay(500)
//            }
//        }
//    }

    val result = withTimeoutOrNull(1300) {
        repeat(100) {
            println("job1: I'm sleeping $it ..")
            delay(500)
        }
        "DONE"
    }
    println("Result is $result")
}