package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import java.io.IOException


/**
 * author: slin
 * date: 2020/4/28
 * description:异常处理
 * 1. 异常传播
 *  1). `launch`（或者`actor`）的异常在协程里面处理，类似于线程的`Thread.uncaughtExceptionHandler`，不会抛到外层协程来
 *  2). `async`(或者`produce`)的异常依赖用户在调用`await`（或`receive`）等地方捕获异常，会传播到协程外面
 * 2. 与` Thread.defaultUncaughtExceptionHandler`一样，可以使用`CoroutineExceptionHandler`添加默认异常处理器
 * 3. 取消异常
 *  1). 子协程取消，父协程不会被取消
 *  2). 子协程异常，会导致父协程被取消，所有子协程都会抛出`CancellationException`
 *  3). 即使设置了`CoroutineExceptionHandler`也不能捕获到取消异常`CancellationException`
 * 4. 异常聚合
 *  1). 当多个子协程同时抛出多个异常，只有第一个异常会抛出，其余异常会被压制，保存在该异常的`suppressed`里面
 *  2). 取消异常是透明的，默认情况下会被解包，不会保存在`suppressed`里面
 * 5. 监督作业（SupervisorJob）
 *  1). 在`SupervisorJob`作用域中，异常导致的协程取消只会向下传播，不会传递给同级或者父协程
 * 6. 监督作用域（supervisorScope）
 *  1). 监督作用域，可以用来替代`coroutineScope`,它只会单向传播，并且当作业本身执行失败的时候将所有子作业取消，
 *      作业自身也会在所有子作业执行结束前等待
 * 7. 常规作业与监督作业的重要区别就是异常处理。监督作业中的每一个子作业都应该通过异常处理机制处理自身的异常，
 *      因为在监督作业中，子作业的执行失败是不会传播给它的父作业
 */
fun main() {
//    exceptionTransmit()
//    defaultExceptionHandler()
//    cancellationExceptionTest()
//    cancellationExceptionTest2()
//    aggregationException()
//    supervisorJobTest()
//    supervisorScopeTest()
    supervisorScopeTest2()
}

/**
 * 异常传播
 * 1. `launch`（或者`actor`）的异常在协程里面处理，类似于线程的`Thread.uncaughtExceptionHandler`，不会抛到外层协程来
 * 2. `async`(或者`produce`)的异常依赖用户在调用`await`（或`receive`）等地方捕获异常，会传播到协程外面
 */
fun exceptionTransmit() = runBlocking {
    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException()
    }
    try {
        job.join()
        println("Join failed job")
    } catch (e: IndexOutOfBoundsException) {
        //这里捕获不到异常
        println("exception unreached")
    }

    val deferred = GlobalScope.async<Unit> {
        println("Throwing exception from async")
        throw ArithmeticException()
    }

    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }

}

/**
 * 添加默认的异常捕获器
 * 1. 与` Thread.defaultUncaughtExceptionHandler`一样，可以使用`CoroutineExceptionHandler`添加默认异常处理器
 */
fun defaultExceptionHandler() = runBlocking {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }

    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }

    val deferred = GlobalScope.async {
        throw ArithmeticException() //不会打印任何东西，依赖用户去调用`await`
    }
    joinAll(job, deferred)

}

/**
 * 取消异常
 * 1. 子协程取消不会影响到父协程
 */
fun cancellationExceptionTest() = runBlocking {
    val child = launch {
        try {
            delay(Long.MAX_VALUE)
        } finally {
            println("Child is cancelled")
        }
    }
    /** `yield` 在此函数里面可以视为一个挂起点，检查父协程是否被取消 */
    yield()
    println("Cancelling child")
    child.cancel()
    child.join()
    yield()
    println("Parent is not cancelled\n")
}

/**
 * 取消异常
 * 1. 子协程异常，会导致父协程被取消，所有子协程都会抛出`CancellationException`
 * 2. 即使设置了`CoroutineExceptionHandler`也不能捕获到取消异常`CancellationException`
 */
fun cancellationExceptionTest2() = runBlocking {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    val job = GlobalScope.launch(handler) {
        //启动第一个子协程
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                withContext(NonCancellable) {
                    println("Children are cancelled, bu exception not handled until children terminate")
                    delay(100)
                    println("The first child finished its non cancellable block")
                }
            }
        }
        //启动第二个子协程
        launch {
            delay(10)
            println("Second thread throws an exception")
            throw ArithmeticException()
        }

    }
    job.join()
}

/**
 * 异常聚合
 * 1. 当多个子协程同时抛出多个异常，只有第一个异常会抛出，其余异常会被压制，保存在该异常的`suppressed`里面
 * 2. 取消异常是透明的，默认情况下会被解包，不会保存在`suppressed`里面
 */
fun aggregationException() = runBlocking {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable with suppressed ${throwable.suppressed?.contentDeepToString()}")
    }
    var job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                throw ArithmeticException()
            }
//            delay(10)
//            throw ArithmeticException()
        }
        launch {
            delay(10)
            throw IOException()
        }
    }
    job.join()

    println()
    job = GlobalScope.launch(handler) {
        val innerJob = launch {
            launch {
                launch {
                    throw IOException()
                }
            }
        }
        try {
            innerJob.join()
        } catch (e: CancellationException) {
            println("Rethrowing CancellationException with original cause")
            throw e
        }
    }
    job.join()
}

/**
 * 监督作业（SupervisorJob）
 * 在`SupervisorJob`作用域中，异常导致的协程取消只会向下传播，不会传递给同级或者父协程
 */
fun supervisorJobTest() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        //启动第一个子协程，这个忽略它的异常（实际不建议这么做）
        val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
            println("First child is failing")
            throw AssertionError("First child is cancelled")
        }
        //启动第二个子协程，监督第一个
        val secondChild = launch {
            firstChild.join()
            //取消了第一个子作业，却没有传播给第二个子作业
            println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                //取消了监督的传播
                println("Second child is cancelled because supervisor is cancelled")
            }
        }
        //等待知道第一个子作业失败且执行完成
        firstChild.join()
        println("Cancelling supervisor")
        supervisor.cancel()
        delay(10)
        println("Second child is cancelled: ${secondChild.isCancelled}")
        secondChild.cancel()
    }
}

/**
 * 监督作用域（supervisorScope）
 * 监督作用域，可以用来替代`coroutineScope`,它只会单向传播，并且当作业本身执行失败的时候将所有子作业取消，
 *      作业自身也会在所有子作业执行结束前等待
 */
fun supervisorScopeTest() = runBlocking {
    try {
//        coroutineScope<Unit> {
        supervisorScope<Unit> {
            val child = launch {
                try {
                    println("Child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            yield()
            println("Throwing exception from scope")
            throw AssertionError()
        }

    } catch (e: AssertionError) {
        println("Caught assertion error")
    }

}

/**
 * 常规作业与监督作业的重要区别就是异常处理。监督作业中的每一个子作业都应该通过异常处理机制处理自身的异常，
 *      因为在监督作业中，子作业的执行失败是不会传播给它的父作业
 */
fun supervisorScopeTest2() = runBlocking {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }

    supervisorScope {
        launch(handler) {
            println("Child throws an exception")
            throw AssertionError()
        }
        println("Scope is completing")
    }
    println("Scope is completed")
}

