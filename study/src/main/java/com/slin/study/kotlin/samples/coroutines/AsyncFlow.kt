package com.slin.study.kotlin.samples.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis


/**
 * author: slin
 * date: 2020/4/17
 * description: 流（Flow）
 * 1. 流与序列：序列（Sequence）通过`yield`发射数据，序列在发射数据时是阻塞的；
 *      流（Flow）通过`emit`发射数据，使用`collect`收集数据，执行时异步的，且要等到`collect`才会发送数据执行
 * 2. 流取消，流收集在遇到挂载函数时是可以取消的，可以通过`withTimeoutOrNull`指定超时时间取消，也可以通过返回值`job`（launchIn才会返回）去取消
 * 3. 流的创建，可以通过`flow`、`flowOf`和`asFlow`来创建一个流；
 *      流是连续的，是按顺序执行的，默认是不会创建新的协程执行；
 *      而且发射数据数据的协程就是`collect`调用的协程，且不能使用`withContext`改变发射数据源的上下文
 * 4. 过渡流 操作符
 *      1). 过渡流操作符：map、filter等
 *      2). 转换流操作符：transform
 *      3). 限长流操作符：take
 * 5. 末端流操作符：
 *      1). collect: 收集流数据
 *      2). toList/toSet: 转换为集合
 *      3). single/first: 确保流只发送了单个值/获取第一个值
 *      4). reduce/fold: 累积操作，累加或者累乘等，fold可以设置初始值
 * 6. flowOn操作符，指定上游操作线程
 * 7. 缓冲操作符
 *      1). buffer 缓存，将上游数据收集起来，一起发送出去，上游并发执行
 *      2). conflate 合并，上游数据发送太快，下游未来得及处理，将抛弃该上游数据
 *      3). collectLatest 处理最新值，当上游数据发送过来，上一个数据的`collectLatest`还未执行完毕，将放弃执行，直接执行最新的数据
 *      `conflate`与`collectLatest`的区别：未执行完毕时，`conflate`抛弃新数据，`collectLatest`放弃执行旧数据的末端操作，直接执行新的数据
 * 8. 组合操作符
 *      1). zip: 将两个流合并成一个，两个流的值一对一对应，多出来的将不输出
 *      2). combine: 将两个流合并成一个，与zip不同的是，只要两个上游任意一个发送了数据，都会发送一个最近的上游数据组合数据给下游
 * 9. 展平流操作符
 *      1). flatMapConcat 将 Flow<Flow<?>>形式的流展开成一个流，和flatMap相似
 *      2). flatMapMerge 与 flatMapConcat 功能一样，但是上游发送数据是并行的
 *      3). flatMapLatest 与 collectLatest 相似，展平流，在发出新流的同时取消之前收集的
 * 10. 流异常
 *      1). catch: 捕获上游异常，异常后不会往下执行，并且不会捕获下游的异常，如果此方法，外部try/catch可以捕获到异常，
 *      2). onCompletion: 相当于finally，当流执行完毕时调用，如果流异常，参数会给出异常，如果正常则为null
 *          值得注意的是如果`catch`写在`onCompletion`前面，那么`onCompletion`是收不到异常的，因为异常会被`catch`
 * 11. 流启动
 *      1). 如果没有末端操作符，流不会启动执行
 *      2). 如果使用`collect`收集流，那么后面的代码会一直等待直到流执行完毕
 *      3). 使用`launchIn`启动流，不会阻塞后面的代码，`launchIn`会绑定`scope`的生命周期，也可以提前通过返回值`job`来取消流
 */

fun main() {
//    sequenceTest()
//    flowTest()
//    flowCancelTest()

//    createFlow()
//    operatorFlow()
//    endOperatorTest()
//    flowContinuous()
    flowOnTest()
//    bufferOperatorTest()
//    combinationOperatorTest()
//    flatMapTest()
//    exceptionFlow()
//    launchFlowTest()

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
 * 流取消，流收集在遇到挂载函数时是可以取消的，可以通过`withTimeoutOrNull`指定超时时间取消，也可以通过返回值`job`去取消
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
 * 过渡流 操作符
 * 1. 过渡流操作符：map、filter等
 * 2. 转换流操作符：transform
 * 3. 限长流操作符：take
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

}

/**
 * 末端流操作符：
 * 1. collect: 收集流数据
 * 2. toList/toSet: 转换为集合
 * 3. single/first: 确保流只发送了单个值/获取第一个值
 * 4. reduce/fold: 累积操作，累加或者累乘等，fold可以设置初始值
 */
fun endOperatorTest() = runBlocking {
    val sum = (1..5)
        .asFlow()
        .map { it * it }
        .reduce { a, b -> a + b }
    println("(1..5) sum: $sum")

    val foldAdd = (1..3)
        .asFlow()
        .fold(10) { acc, value ->
            acc + value
        }
    println("(1..3) fold: $foldAdd")

    val first = flowOf(1, 2, 3, 4).first()
    val single = flowOf(1).single()
    println("first: $first")
    println("single: $single")
}

/**
 * 流是连续的，是按顺序执行的，默认是不会创建新的协程执行，
 * 而且发射数据数据的协程就是`collect`调用的协程，且不能使用`withContext`改变发射数据源的上下文
 */
fun flowContinuous() = runBlocking {
    (1..5).asFlow()
        .filter {
            log("filter $it")
            it % 2 == 0
//            true
        }.map {
            log("map $it")
            "string $it"
        }.collect {
            log("Collect $it")
        }

    println()
    internalFoo().collect {
        log("Collected $it")
    }
}


private fun internalFoo() = flow {
    log("Started foo flow")
    //发射数据的地方不能切换协程，否则会报错
//    withContext(Dispatchers.Default){
    repeat(3) {
        delay(50)
        log("emit $it")
        emit(it)
    }
//    }
}

/**
 * flowOn操作符
 * 指定上游操作线程
 */
fun flowOnTest() = runBlocking {
    val flow = flow {
        repeat(10) {
            delay(100)
            log("emit：$it")
            emit(it)
        }
    }//.flowOn(Dispatchers.Default)
        .filter {
            log("filter $it")
            it % 2 == 0
        }.flowOn(Dispatchers.IO)

    flow.map {
        log("map $it")
        "string $it"
    }.collect {
        println("Collected $it")
    }
}

/**
 * 缓冲操作符
 * 1. buffer 缓存，将上游数据收集起来，一起发送出去，上游并发执行
 * 2. conflate 合并，上游数据发送太快，下游未来得及处理，将抛弃上游数据
 * 3. collectLatest 处理最新值，当上游数据发送过来，上一个数据的`collectLatest`还未执行完毕，将放弃执行，直接执行最新的数据
 */
fun bufferOperatorTest() = runBlocking {
    val collectAction: suspend (value: Int) -> Unit = { a: Int ->
        delay(150)
        log("Collecting $a")
    }
    var time = measureTimeMillis {
        internalFoo().collectLatest(collectAction)
    }
    println("normal: Collected in $time ms\n")

    time = measureTimeMillis {
        internalFoo().buffer()
            .collectLatest(collectAction)
    }
    println("buffer: Collected in $time ms\n")

    time = measureTimeMillis {
        //这里只会打印两个数字，因为上游发送数字2时，collect还未处理完数字1
        internalFoo().conflate()
            .collect {
                log("Collecting $it")
                delay(300)      //假装collect里面处理了很长时间
                log("Done $it")
            }
    }
    println("conflate: Collected in $time ms\n")

    time = measureTimeMillis {
        internalFoo().collectLatest {
            log("Collecting $it")
            delay(300)      //假装collect里面处理了很长时间
            log("Done $it")
        }
    }
    println("collectLatest: Collected in $time ms\n")
}

/**
 * 组合操作符
 * 1. zip: 将两个流合并成一个，两个流的值一对一对应，多出来的将不输出
 * 2. combine: 将两个流合并成一个，与zip不同的是，只要两个上游任意一个发送了数据，都会发送一个最近的上游数据组合数据给下游
 */
fun combinationOperatorTest() = runBlocking {
    val numFlow = (1..4).asFlow()
    val strFlow = flowOf("one", "two", "three")
    numFlow.zip(strFlow) { a, b -> "$a -> $b" }
        .collect {
            log("Collecting $it")
        }

    println()
    val numFlowDelay30 = numFlow.onEach { delay(30) }
    val strFlowDelay40 = strFlow.onEach { delay(40) }
    val startTime = System.currentTimeMillis()
    numFlowDelay30.combine(strFlowDelay40) { a, b -> "$a -> $b" }
        .collect {
            log("Collecting $it at ${System.currentTimeMillis() - startTime} from start")
        }
}

private fun internalRequestFlow(num: Int): Flow<String> {
    return flow {
        emit("request $num")
        delay(50)
        emit("response $num")
    }
}

/**
 * 展平流
 * 1. flatMapConcat 将 Flow<Flow<?>>形式的流展开成一个流，和flatMap相似
 * 2. flatMapMerge 与 flatMapConcat 功能一样，但是上游发送数据是并行的
 * 3. flatMapLatest 与 collectLatest 相似，展平流，在发出新流的同时取消之前收集的
 */
fun flatMapTest() = runBlocking {
    val flow: Flow<Int> = (1..3).asFlow().onEach { delay(10) }

    val fflow: Flow<Flow<String>> = flow.map {
        internalRequestFlow(it)
    }
    fflow.collect { f ->
        f.collect {
            log("Collecting $it")
        }
    }

    println()

    var startTime = System.currentTimeMillis()
    flow.flatMapConcat {
        internalRequestFlow(it)
    }.collect {
        log("flatMapConcat: Collecting $it at ${System.currentTimeMillis() - startTime} ms from start")
    }

    println()

    startTime = System.currentTimeMillis()
    flow.flatMapMerge {
        internalRequestFlow(it)
    }.collect {
        log("flatMapMerge: Collecting $it at ${System.currentTimeMillis() - startTime} ms from start")
    }

    println()
    startTime = System.currentTimeMillis()
    flow.flatMapLatest {
        internalRequestFlow(it)
    }.collect {
        log("flatMapLatest: Collecting $it at ${System.currentTimeMillis() - startTime} ms from start")
    }
}

/**
 * 流异常
 * 1. catch: 捕获上游异常，异常后不会往下执行，并且不会捕获下游的异常，如果此方法，外部try/catch可以捕获到异常，
 * 2. onCompletion: 相当于finally，当流执行完毕时调用，如果流异常，参数会给出异常，如果正常则为null
 *      值得注意的是如果`catch`写在`onCompletion`前面，那么`onCompletion`是收不到异常的，因为异常会被`catch`
 */
fun exceptionFlow() = runBlocking {
    //收集数据异常
    try {
        internalFoo().collect { value ->
            log("Collecting $value")
            check(value < 1) { "Checked $value" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }

    println()
    //操作符异常
    try {
        internalFoo()
            .map {
                check(it < 1) { "Crashed on $it" }
                "String $it"
            }
            .collect { value ->
                log("Collecting $value")
            }
    } catch (e: Throwable) {
        println("Caught $e")
    }

    println()
    try {
        internalFoo()
            .map {
                check(it <= 1) { "map: Check $it" }
                it
            }
            //捕获上游异常，异常后不会往下执行，并且不会捕获下游的异常，且外部try/catch将捕获不到异常
            .catch { e ->
                log("catch: Caught $e")
                -100
            }
            .collect { value ->
                log("Collecting $value")
//                check(value < 1) { "Checked $value" }
            }
    } catch (e: Throwable) {
        println("Caught $e")
    }

    //finally 流完成
    println()
    try {
        internalFoo().collect { log("Collecting $it") }
    } finally {
        log("Done")
    }

    println("\nonCompletion1: ")
    internalFoo()
        .onCompletion { log("Done. $it") }
        .collect { log("Collecting $it") }

    println("\nonCompletion2: ")
    internalFoo().onEach {
        check(it <= 1) { "Checking $it" }
    }
        .onCompletion { cause -> log("onCompletion: $cause") }
        .catch { e -> log("catch: $e") }
        .collect { log("Collecting $it") }

}

/**
 * 流启动
 * 1. 如果没有末端操作符，流不会气动执行
 * 2. 如果使用`collect`收集流，那么后面的代码会一直等待直到流执行完毕
 * 3. 使用`launchIn`启动流，不会阻塞后面的代码，`launchIn`会绑定`scope`的生命周期，也可以提前通过返回值`job`来取消流
 */
fun launchFlowTest() = runBlocking {
    val delayTime = 1000L

    //如果没有末端操作符，那么流是不会执行的
    internalFoo().onEach { event -> log("Event: $event") }

    //流收集会等待
    println()
    internalFoo()
        .onEach { delay(delayTime) }
        .onEach { event -> log("Event: $event") }
        .collect()
    println("Done")

    println()
    val job = internalFoo()
        .onEach { delay(delayTime) }
        .onEach { event -> log("Event: $event") }
        .launchIn(this)
    println("Done")

    launch {
        delay(delayTime * 2)
        job.cancel()
    }

}
