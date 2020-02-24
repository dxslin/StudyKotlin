package com.slin.study.kotlin.test.lambda

import com.slin.study.kotlin.test.`class`.Person

/**
 * author: slin
 * date: 2020-01-29
 * description:
 */

fun main() {

    val employees = listOf(
        Person("tom", 7000),
        Person("jay", 10000), Person("slin", 12000)
    )
    /**
     * 使用lambda表达式寻早工资最高的人
     * it代指列表的里面的元素
     */
    println("max salary: ${employees.maxBy { it.salary }}")
    //成员引用，使用双冒号可以直接引用属性或者方法
    println("max salary: ${employees.maxBy(Person::salary)}")
    //成员引用 引用构造函数，相当于C的函数指针
    val createPerson = ::Person
    val person = createPerson("slin", 10000)
    println("person: $person")

    //将lambda作为参数传递
    var names = employees.joinToString(separator = " ", transform = { p: Person -> p.name })
    println("names: $names")
    //当只有一个lambda作为参数时可以放在括号外面
    names = employees.joinToString(separator = " ") { p: Person -> p.name }
    println("names: $names")

    val errors = listOf("403 Forbidden", "404 Not Found", "500 Server Error")
    printMessageWithPrefix(errors, "Error: ")
    printProblemCounts(errors)

    //可以将代码块作为变量赋值
    val sum = { x: Int, y: Int -> x + y }
    println("sum(1, 2): ${sum(1, 2)}")
    println("加法：compute(1, 2, sum): ${compute(1, 2, sum)}")
    println(
        "乘法：compute(2, 3) { x:Int, y:Int -> x * y}: ${compute(
            2,
            3
        ) { x: Int, y: Int -> x * y }}"
    )
}

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    //lambda表达式里面可以访问外部的局部变量，而且是非final的
    messages.forEach {
        println("$prefix $it")
    }

}

fun printProblemCounts(messages: Collection<String>) {
    var clientError = 0
    var serverError = 0
    //lambda表达式可以访问且修改局部变量，非final的
    messages.forEach {
        if (it.startsWith("4")) {
            clientError++
        } else if (it.startsWith("5")) {
            serverError++
        }
    }
    println("clientError: $clientError, serverError: $serverError")
}


/**
 * 定义需要lambda为参数的方法
 *
 */
fun compute(x: Int, y: Int, expression: (Int, Int) -> Int): Int {
    return expression(x, y)
}

