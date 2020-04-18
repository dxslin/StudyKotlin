package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull


/**
 * author: slin
 * date: 2020/4/17
 * description:
 *
 */

fun main() {
//    sequenceTest()
//    flowTest()
//    flowCancelTest()
//    createFlow()
    operatorFlow()

}

/**
 * 序列
 * 1. 序列（Sequence）通过`yield`发射数据，序列在发射数据时是阻塞的
 */
fun sequenceTest() = runBlocking {
    sequence<Int> {
        for (i in 1..3) {
            Thread.sleep(100)   //模拟执行其他任务
            yield(i)     //发射数据源
        }
    }.forEach {
        println("sequence $it")
    }
    //启动一个协程查看主线程是否被阻塞
    launch {
        repeat(3) {
            println("I'm not blocked $it")
            delay(100)
        }
    }
}

/**
 * 流
 * 1. 流（Flow）通过`emit`发射数据，使用`collect`收集数据，执行时异步的，且要等到`collect`才会发送数据执行
 */
fun flowTest() = runBlocking {
    flow<Int> {
        for (i in 1..3) {
            delay(100)   //模拟执行其他任务
            emit(i)     //发射数据源
        }
    }.collect {
        println("flow: $it")
    }

    //启动一个协程查看主线程是否被阻塞
    launch {
        repeat(3) {
            println("I'm not blocked $it")
            delay(100)
        }
    }
}

/**
 * 流取消，流收集在遇到挂载函数时可以取消
 */
fun flowCancelTest() = runBlocking {
    val flow = flow {
        println("Flow started")
        repeat(3) {
            delay(100)
            println("Emitting $it")
            emit(it)
        }
    }

    withTimeoutOrNull(250) {
        flow.collect {
            println(it)
        }
    }
    println("Done")
}

/**
 * 流的创建，可以通过`flow`、`flowOf`和`asFlow`来创建一个流
 *
 */
fun createFlow() = runBlocking {
    (1..3).asFlow().collect {
        println("asFlow: $it")
    }
    flowOf(1, 2, 3).collect {
        println("flowOf: $it")
    }
    flow {
        repeat(3) {
            emit(it)
        }
    }.collect {
        println("flow: $it")
    }
}

/**
 * 流 操作符
 * 1. 过渡流操作符：map、filter等
 * 2. 转换流操作符：transform
 * 3. 限长流操作符：take
 * 4. 末端流操作符：collect、toList/toSet（转换为集合）、
 */
fun operatorFlow() = runBlocking {
    (1..10).asFlow()
        .filter { it % 5 == 0 }
        .map {
            //使用`map`做类型转换
            delay(100)
            "Request $it"
        }.transform {
            //也是类型转换，不过比`map`更复杂，可以发送多个数据出去
            emit("Transform request: $it")
            emit("Transform response: $it")
        }.collect {
            println(it)
        }

    flow {
        try {
            emit(1)
            emit(2)
            println("this line will not execute")
            emit(3)
        } finally {
            println("Finally in number")
        }
    }
        .take(2)        //限长操作符，操作后会抛出异常取消协程
        .collect {
            println(it)
        }

    val sum = (1..5)
        .asFlow()
        .map { it * it }
        .reduce { a, b -> a + b }
    println("(1..5) sum: $sum")

    val first = flowOf(1, 2, 3, 4).first()
    val single = flowOf(1).single()
    println("first: $first")
    println("single: $single")
}

fun foo() = flow {
    log("Started foo flow")
    repeat(3) {
        emit(it)
    }
}

fun contextFlow() = runBlocking {

}
