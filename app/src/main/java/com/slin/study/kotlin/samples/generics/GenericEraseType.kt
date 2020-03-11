package com.slin.study.kotlin.samples.generics

import android.app.Service
import java.util.*


/**
 * author: slin
 * date: 2020/3/10
 * description:运行时的泛型，擦除和实体化类型参数
 * 1. 泛型在运行时是被类型擦除了的，是无法判断实体究竟包含哪些类型
 * 2. 强制转换时是无法判断具体的泛型类型的，能够强制转换成功，但是后面调用时极有可能异常
 * 3. `reified`关键字可以声明实体化类型，不会被类型擦除，只能在内联函数中使用
 * 4. 实体化类型参数可以替代类引用，简化代码
 */

@Suppress("USELESS_IS_CHECK")
fun main() {
    val listString: List<String> = listOf("1", "2", "3")
    val listInt: List<Int> = listOf(1, 2, 3)
    val listMix: List<Any> = listOf("one", "two", 1, 2, "three")

    println("listString is List : ${listString is List}")
    println("listString is List<*> : ${listString is List<*>}")
    println("listString is List<String> : ${listString is List<String>}")

    printSum(listInt)
    //Set不是List，所以强制转换失败，抛出IllegalArgumentException异常
    printSum(setOf(1, 2, 3))
    //这里能够成功强制转换，但是会抛出ClassCastException异常，而非IllegalArgumentException
//    printSum(listString)

    println("isA<String>(\"abs\") : ${isA<String>("abs")}")
    println("isA<String>(123) : ${isA<String>(123)}")
    println("listMix.filterIsInstance<String>() : ${listMix.filterIsInstance<String>()}")

    ServiceLoader.load(Service::class.java)
    loadService<Service>()


}

/**
 * 对泛型做强制转换
 * 1. 强制转换时是无法判断具体的泛型类型的，能够强制转换成功，但是后面调用时极有可能异常
 */
@Suppress("UNCHECKED_CAST")
fun printSum(c: Collection<*>) {
    try {
        val intList = c as? List<Int> ?: throw IllegalArgumentException("List is expected")
        println(intList.sum())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 因为类型擦除了，所以这里是无法直接使用T判断的
 */
//fun <T> isA(value: Any) = value is T

/**
 * 声明带实体化类型参数的函数
 * 1. 使用内联函数和`reified`标记类型参数即可实化类型参数，即可用于检查类型
 * 2. `reified`关键字声明的类型是不会被类型擦除的，`reified`只能在内联函数(inline)中使用
 */
inline fun <reified T> isA(value: Any) = value is T

inline fun <reified T> Iterable<*>.filterIsInstance(): List<T> {
    val destination = mutableListOf<T>()
    for (element in this) {
        if (element is T) {
            destination.add(element)
        }
    }
    return destination
}

/**
 * 使用实体化类型参数代替类引用，简化代码
 */
inline fun <reified T : Service> loadService(): ServiceLoader<T>? {
    return ServiceLoader.load(T::class.java)
}
