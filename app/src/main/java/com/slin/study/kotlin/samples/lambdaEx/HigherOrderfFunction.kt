package com.slin.study.kotlin.samples.lambdaEx

import java.util.*

/**
 * author: slin
 * date: 2020-02-29
 * description: 高阶函数，即以另一个函数作为参数或者返回值的函数
 * 1. 函数类型，格式“(参数类型) -> 返回值类型”
 * 2. 定义参数为函数类型的函数
 * 3. 定义返回值为函数类型的函数
 */

fun main() {
    /**
     * 函数类型，格式“(参数类型) -> 返回值类型”，
     * 小括号中填写参数值类型，多个用逗号`,`分割，后面紧跟`->`和返回值参数
     */
    val sum: (Int, Int) -> Int = { x, y -> x + y }
    //没有返回值，就是Unit
    val action: () -> Unit = { println("Hello Slin") }
    //返回值可为空的函数类型
    val canReturnNull: (Int, Int) -> Int? = { _, _ -> null }
    //可为空的函数类型
    val funOrNull: ((Int, Int) -> Int)? = null

    twoAndThree(sum)
    twoAndThree { x, y -> x * y }

    println("filter: ${"abc1d2fc3e".filter { c -> c in 'a'..'z' }}")

    val letters = listOf("Alpha", "Beta")
    println("joinToString: ${letters.joinToString(transform = { it.toLowerCase(Locale.ROOT) })}")
    println(
        "joinToString: ${letters.joinToString(
            separator = "!",
            transform = { it.toUpperCase(Locale.ROOT) })}"
    )

    println("joinToString: ${letters.joinToStringNull(separator = ". ")}")

    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
    println("Shipping costs ${calculator(Order(3))}")

    val contacts = listOf(
        PersonPhone("Dmitry", "Jemerov", "123-4567"),
        PersonPhone("Svetlana", "Isakova", null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(contacts.filter(contactListFilters.getPredicate()))

}

/**
 * 定义使用lambda做参数的函数
 */
fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

/**
 * 实现一个简化版的filter
 */
fun String.filter(predicate: (Char) -> Boolean): String {
    val builder = StringBuilder()
    forEach { c ->
        if (predicate(c)) {
            builder.append(c)
        }
    }
    return builder.toString()
}

fun <T> Collection<T>.joinToString(
    separator: String = ",",
    prefix: String = "",
    postfix: String = "",
    transform: (T) -> String = { it.toString() }
): String {
    val sb = StringBuilder(prefix)
    for ((index, element) in withIndex()) {
        if (index > 0) sb.append(separator)
        sb.append(transform(element))
    }
    sb.append(postfix)
    return sb.toString()
}

/**
 * 使用可为空的函数类型作为参数
 */
fun <T> Collection<T>.joinToStringNull(
    separator: String = ",",
    prefix: String = "",
    postfix: String = "",
    transform: ((T) -> String)? = null
): String {
    val sb = StringBuilder(prefix)
    for ((index, element) in withIndex()) {
        if (index > 0) sb.append(separator)
        //函数类型为空时调用使用`?.invoke`来调用，这样可以避免不为空检查
        val str = transform?.invoke(element) ?: element.toString()
        sb.append(str)
    }
    sb.append(postfix)
    return sb.toString()
}

enum class Delivery { STANDARD, EXPEDITED }
class Order(val itemCount: Int)

/**
 * 定义返回值为函数类型的函数
 */
fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
    }
    return { order -> 1.2 * order.itemCount }
}

data class PersonPhone(val firstName: String, val lastName: String, val phoneNumber: String?)
class ContactListFilters {
    var prefix = ""
    var onlyWithPhoneNumber: Boolean = false

    /**
     * 返回一个函数类型的表达式
     */
    fun getPredicate(): (PersonPhone) -> Boolean {
        val startWithPrefix = { p: PersonPhone ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber) {
            return startWithPrefix
        }
        return { startWithPrefix(it) && it.phoneNumber != null }
    }

}