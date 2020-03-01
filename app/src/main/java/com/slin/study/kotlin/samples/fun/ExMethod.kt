package com.slin.study.kotlin.samples.`fun`

import java.io.File

/**
 * author: slin
 * date: 2019-11-19
 * description:扩展函数测试
 */

fun main() {
    /**
     * 可变参数是通过"vararg"描述，详细可以查看listOf实现
     */
    val list = listOf(1, 2, 3, 4)

    println("重载测试: ")
    println(list.joinToString(separator = " ", prefix = "\"", postfix = "\""))

    println("\n扩展函数重写测试")
    val view: View =
        Button()
    view.showOff()

    println("\n扩展属性测试：")
    val sb = StringBuilder("kotlin?")
    println(sb.lastChar)
    sb.lastChar = '!'
    println(sb.lastChar)

    /**
     * 中缀调用：1.to("one") 可以简写为 1 to "one"
     *          可以中缀调用的方法是使用“infix”修饰的，可以查看“to”的实现方式
     * 解构声明：Pair，map，数组的.withIndex()这些都是可以解构的
     *
     */
    println("中缀调用测试")
    val map = mapOf(1 to "one", 2 to "two", 3.to("three"))
    println("1.to(\"one\") -> 1 to \"one\"")
    println("解构声明测试")
    for ((key, value) in map) {
        println("$key: $value")
    }
    val (number, name) = 4 to "four"
    println("$number: $name")

    /**
     * 1. Java字符串使用split(".")分割会得到空数据，但是kotlin可以正常使用
     * 2. String.toRegex()可以显示创建一个正则表达式
     * 3.
     */
    println("字符串和正则表达式测试")
    println("12.345-6.A".split("."))
    println("12.345-6.A".split("[.\\-]".toRegex()))
    println("12.345-6.A".split(".", "-"))

    val file = File("kotlin_test.txt")
    println("file path: ${file.absolutePath}")
    parsePath(file.absolutePath)
    parsePath2(file.absolutePath)

    println("局部函数测试")
    val user = User(10, "slin", "")
    try {
        saveUser(user)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    try {
        user.save()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

/**
 * 扩展函数，重载，方法参数可选输入
 */
fun <T> Collection<T>.joinToString(
    separator: String = ",",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

/************************ 扩展函数 ***************************************/
open class View

class Button : View()

/**
 * 扩展函数不存在重写
 * 原因是其实现原理是生成静态函数，所以是不可以重写的
 */
fun View.showOff() = println("I'm a View")

fun Button.showOff() = println("I'm a Button")

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value) {
        this.setCharAt(length - 1, value)
    }
/*************************************************************************/

/**
 * 使用扩展函数分割字符串解析文件路径
 */
fun parsePath(path: String) {
    val directory = path.substringBeforeLast("\\")
    val fullName = path.substringAfterLast("\\")

    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    println("字符串分割： Dir: $directory, name: $fileName ,ext:$extension")
}

/**
 * 使用正则表达式分割字符串解析文件路径
 * 三重引号测试
 */
fun parsePath2(path: String) {
    /**
     * 正常引号需要对“\”转义，转换为正则表达式时，“\”就需要转义两次才行，即“\\\\”
     */
//    val regex2 = "(.+)\\\\(.+)\\.(.+)".toRegex()
    /**
     * 三重引号可以不用对“\”转义，所以转换为正则表达式时只需要转义一次
     */
    val regex = """(.+)\\(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    println("matchResult: $matchResult")
    if (matchResult != null) {
        //这里也用到了解构声明
        val (directory, fileName, extension) = matchResult.destructured
        println("正则表达式分割： Dir: $directory, name: $fileName ,ext:$extension")
    }

    println("三重引号测试")
    val kotlinLogo = """| //
                                .|//
                                .|/ \""".trimMargin(".")
    println(kotlinLogo)
}

/**
 * 局部函数测试
 * 1. 函数内部可以定义局部函数
 * 2. 局部函数可以访问外层函数参数
 * 3. 局部函数可以访问类成员变量
 */
class User(val id: Int, val name: String, val address: String)

/**
 * 局部函数访问外层方法参数
 */
fun saveUser(user: User) {
    fun validate(value: String, filed: String) {
        if (value.isEmpty()) {
            throw Exception("Can't save user ${user.id}: empty $filed")
        }
    }
    validate(user.name, "Name")
    validate(user.address, "Address")
}

/**
 * 局部函数访问类成员变量
 */
fun User.save() {
    fun validate(value: String, filed: String) {
        if (value.isEmpty()) {
            throw Exception("Can't save user ${id}: empty $filed")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}
