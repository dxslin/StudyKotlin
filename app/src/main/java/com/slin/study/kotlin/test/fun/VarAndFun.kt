package com.slin.study.kotlin.test.`fun`

fun main(args: Array<String>) {
    //声明只读变量
    val hello = "hello kotlin"
    //声明可变变量，变量后面可以跟“:类型”指定变量类型，也可以省略
    var answer: Int = 12
    answer = 13
    println(hello)
    println(answer)
    println(max(1, 2))
    println(maxSimple(3, 2))
    varTest()
    strTemplateTest()
    val rectangle = Rectangle(10, 10)
    println("rectangle is square: ${rectangle.isSquare}")
    println("rectangle width: ${rectangle.width} height: ${rectangle.height}")
    rectangle.color = 11
    println("rectangle color: ${rectangle.color} colorString: ${rectangle.colorString}")
    rectangle.colorString = "12"
    println("rectangle color: ${rectangle.color} colorString: ${rectangle.colorString}")


}

/**
 * 1. fun用于声明函数
 * 2. 参数都是可变变量，必须使用指定变量类型
 * 3. 函数小括号后面跟函数返回值类型
 */
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

/**
 * 1. 函数可以简写成表达式函数
 */
fun maxSimple(a: Int, b: Int) = if (a > b) a else b

fun varTest(){
    val m = max(1, 2)
    val message: String
    message = if (m > 1){
        ">1"
    } else{
        "<1"
    }
    println("m is $message")
}

fun strTemplateTest(){
    //kotlin 提供了诸如“arrayListOf()”这样的扩展函数快捷的创建List
    val strList = arrayListOf("tom", "june", "slin", "cat")
    //在字符串里面使用“${变量名}”可以直接将变量拼接到字符串中
    println("strList is $strList")
    println("strList[0] is ${strList[0]}")
    //可以在“${表达式}”使用复杂的表达式语句
    println(
        "strList size is ${if (strList.size > 0) "" + (strList.size + Math.max(
            1,
            3
        )) else "zero"}"
    )

}


/**
 * 1. 与Java一样，class声明类
 * 2. kotlin的类默认是pulic的
 * 3. 类名后面可以直接跟小括号和成员变量，声明构造函数和成员变量
 * 4. 冒号后面跟需要继承的父类或者需要实现的接口
 */
class Rectangle(val height: Int, val width: Int) : View() {

    //var变量会自动生成setter和getter
    var color: Int = 0

    //通过get()和set()可以重写 getter和setter
    var colorString: String
        get() = color.toString()
        set(value) {
            color = value.toInt()
        }

    // val 声明成员变量只有getter
    val isSquare: Boolean
        get() = height == width

}