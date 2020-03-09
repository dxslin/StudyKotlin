package com.slin.study.kotlin.samples.generics

/**
 * author: slin
 * date: 2020-03-09
 * description: 泛型
 * 1. kotlin中声明和使用泛型与Java大致相同
 * 2. 使用“T:类型”来指定泛型的上界，表示T代表的类型必须是指定的上界类型或者其子类型
 * 3. 通过关键字`where`指定多个约束，“where T:类型, T:类型 ...”。
 * 4. 当没有指定泛型上界时，默认时`Any?`，它是可以为空的，如果不能为空，请显示指出上限`Any`
 *
 */

fun main() {
    val letters = ('a'..'z').toList()
    println("letters.slice(1..4): + ${letters.slice(1..4)}")
    println("letters.slice(10..15): ${letters.slice(10..15)}")
    println("letters.filter { it.toInt() < 10 }: ${letters.filter { it.toInt() < 10 }}")

    val stringList = StringList()
    stringList.add("hello")
    stringList.add("world")
    stringList.add("slin")
    println(stringList[1])

    println("(0..10).toList().sum(): ${(0..10).toList().sum()}")
    println(max(100, 99))

    val sb = StringBuilder("hello world")
    println("ensureTrailingPeriod(sb): ${ensureTrailingPeriod(sb)}")

    val processor = Processor<String?>()
    processor.process(null)
    val processorNonNull = ProcessorNonNull<String>()
    processorNonNull.process("slin")
}


/**
 * 定义一个泛型函数
 */
fun <T> List<T>.slice(indices: IntRange): List<T> {
    return subList(indices.first, indices.last)
}

/**
 * 泛化高阶函数
 */
fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    forEach {
        if (predicate(it)) {
            list.add(it)
        }
    }
    return list
}

/**
 * 声明泛型接口
 * 1. 在接口上面声明了泛型参数T，在接口或者类的内部即可正常使用该类型，无论是返回类型还是参数类型
 */
interface SlinList<T> {
    //返回值使用泛型类型
    operator fun get(index: Int): T

    //参数使用泛型类型
    fun add(element: T)
}

class StringList : SlinList<String> {
    private val mData: MutableList<String> = mutableListOf()

    override fun get(index: Int): String {
        return mData[index]
    }

    override fun add(element: String) {
        mData.add(element)
    }
}

/**
 * 类型参数约束
 * 1. 使用“T:类型”来指定泛型的上界，表示T代表的类型必须是指定的上界类型或者其子类型
 */
fun <T : Number> List<T>.sum(): Double {
    var sum = 0.0
    forEach { sum += it.toDouble() }
    return sum
}

/**
 * 声明带类型参数约束的函数
 *  这里指出函数的实参必须是实现了`Comparable`接口的类型
 */
fun <T : Comparable<T>> max(first: T, second: T): T {
    return if (first > second) first else second
}

/**
 * 为一个类型参数指定多个约束
 * 1. 通过关键字`where`指定多个约束，“where T:类型, T:类型 ...”
 * 2. 参数类型必须同时满足多个约束才可以正常操作
 */
fun <T> ensureTrailingPeriod(seq: T): T where T : CharSequence, T : Appendable {
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
    return seq
}

/**
 * 1. 当没有指定泛型上界时，默认时`Any?`，它是可以为空的
 * 2. 如果不能为空，请显示指出上限`Any`
 */
class Processor<T> {
    fun process(value: T) {
        value?.hashCode()
    }
}

class ProcessorNonNull<T : Any> {
    fun process(value: T) {
        value.hashCode()
    }
}