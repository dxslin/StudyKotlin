package com.slin.study.kotlin.test.grammer

fun main(args: Array<String>) {
    println("hello world")
    println(max(1, 2))
    println(maxSimple(3, 2))
    varTest()
    strTemplateTest()
    val rectangle = Rectangle(10, 10);
    println("rectangle is square: ${rectangle.isSquare}")
    println("rectangle width: ${rectangle.width} height: ${rectangle.height}")

}

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

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
    val strList = arrayListOf("tom", "june", "slin", "cat");
    println("strList is $strList")
    println("strList[0] is ${strList[0]}")
    println("strList size is ${if(strList.size > 0) "" + strList.size else "zero" }")
}

class Rectangle( val height: Int,  val width: Int){
    val isSquare: Boolean
        get() = height == width
}