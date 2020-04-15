package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*


/**
 * author: slin
 * date: 2020/4/10
 * description:协程上下文和调度器
 * 1. 不同的调度器
 *  调度器                             作用
 *  未填写                             承袭父协程的上下文，一直运行在主线程中
 *  Dispatchers.Unconfined             非受限的，运行在主线程中直到第一个挂起点，恢复后不一定在主线程中
 *  Dispatchers.Default                运行在默认的线程池中
 *  newSingleThreadContext("xxx")      创建一个新的线程运行
 * 2. 调试协程与线程，打印协程名称：运行时添加 `-Dkotlinx.coroutines.debug` JVM参数即可在打印线程名时同时打印协程名
 * 3. 使用`withContext`切换线程
 * 4. 子协程：当一个协程在CoroutineScope中启动，它将通过CoroutineScope.coroutineContext承袭上下文，
 *      并且新协程启动的Job被称为父协程的子作业，当一个父协程取消时，所有的子协程都会被递归取消，
 *      父协程总是会等待所有的子协程执行完毕
 * 5. 可以使用`CoroutineName`为协程命名，使用`+`组合上下文元素
 *
 */
@ObsoleteCoroutinesApi
fun main() {

//    dispatcherTest()
//    unconfinedTest()
//    debugTest()
//    switchContextTest()
//    cancelContextTest()
//    namedCoroutineTest()
//    scopeUseTest()
    threadLocalValueTest()
}

/**
 * 设置不同的调度器
 */
@ObsoleteCoroutinesApi
fun dispatcherTest() = runBlocking {
    //运行在父协程的上下文，即`runBlocking`的主线程中
    launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
    }
    //不受限的 —— 将工作在主线程中
    launch(Dispatchers.Unconfined) {
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
    }
    //将会获取默认调度器
    launch(Dispatchers.Default) {
        println("Default: I'm working in thread ${Thread.currentThread().name}")
    }
    //将使它获得一个新的线程，再不需要使用时可以调用`close`关闭
    launch(newSingleThreadContext("MyOwnThread")) {
        println("newThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}

/**
 * 非受限调度器和受限调度器的区别
 */
fun unconfinedTest() = runBlocking {
    // 非受限的 —— 会在当前线程中创建一个协程，工作在主线程中直到遇到第一个挂起点，
    // 之后恢复的线程会有被挂起的函数决定，不一定依然在主线程中
    launch(Dispatchers.Unconfined) {
        println("Unconfined:  I'm working in thread ${Thread.currentThread().name}")
        delay(500)

        //挂起恢复后不一定
        println("Unconfined: After delay in thread ${Thread.currentThread().name}")
    }
    // 父协程的上下文中，主`runBlocking`中运行，即使挂起后恢复依然是
    launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
}

/**
 * 调试协程与线程
 * 打印协程名称：运行时添加 `-Dkotlinx.coroutines.debug` JVM参数即可在打印线程名时同时打印协程名
 */
fun debugTest() = runBlocking {
    val a = async {
        log("I'm computing a piece of the answer");
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}

/**
 * 切换不同的线程
 * 通过`runBlocking`显示指定`ctx1`为上下文，然后通过`withContext`修改上下文为`ctx2`，回来之后依然是该协程
 * `use`可以用来释放线程资源
 */
@ObsoleteCoroutinesApi
fun switchContextTest() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Start in ctx1")
                withContext(ctx2) {
                    log("Working on ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}

/**
 * 子协程：当一个协程在`CoroutineScope`中启动，它将通过`CoroutineScope.coroutineContext`承袭上下文，
 *      并且新协程启动的Job被称为父协程的子作业，当一个父协程取消时，所有的子协程都会被递归取消，
 *      父协程总是会等待所有的子协程执行完毕
 */
fun cancelContextTest() = runBlocking {
    val request = launch {
        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently")
            delay(1000)
            println("job1: I am not effected by cancellation of the request")
        }
        launch {
            delay(100)
            println("job2: I'm a child of the request coroutine");
            delay(1000)
            println("job2: I will not execute this line if my request is canceled")
        }
    }
    delay(500)
    request.cancel();
    delay(1000)
    println("main: Who has survived request cancellation?")
}

/**
 * 使用`CoroutineName`为协程命名
 * 使用`+`组合上下文元素
 */
fun namedCoroutineTest() = runBlocking {
    log("Started main coroutine")
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(100)
        log("Computing v1")
        10
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(200)
        log("Computing v2")
        5
    }
    log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
    //使用`+`组合上下文元素
    launch(Dispatchers.Default + CoroutineName("test")) {
        log("Combine some element.")
    }

}


class CoroutineActivity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun destroy() {
        cancel()
    }

    fun doSomeThing() {
        //启动10个协程，每个工作不同的时长
        repeat(10) { i ->
            launch {
                //延时200ms、400ms、600ms等不同时间
                delay((i + 1) * 200L)
                println("Coroutine $i done")
            }
        }
    }
}

/**
 * 协程作用域测试
 */
fun scopeUseTest() = runBlocking {
    val activity = CoroutineActivity()
    activity.doSomeThing()
    println("Launched coroutines")
    delay(610L)
    println("Destroying activity!")
    activity.destroy()
    delay(1000)
}

fun threadLocalValueTest() = runBlocking {
    val threadLocal = ThreadLocal<String>()
    threadLocal.set("main")
    log("pre main: thread local value: ${threadLocal.get()}")

    //使用`asContextElement`设置协程中`ThreadLocal`的值
    val job = launch(Dispatchers.Default + threadLocal.asContextElement("launch")) {
        log("launch start: thread local value: ${threadLocal.get()}")
        yield()
        log("after yield: thread local value: ${threadLocal.get()}")
        withContext(this@runBlocking.coroutineContext) {
            log("with main context: thread local value: ${threadLocal.get()}")
        }
    }
    job.join()
    log("post main: thread local value: ${threadLocal.get()}")
}







