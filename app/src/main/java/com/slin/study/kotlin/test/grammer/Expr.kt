package com.slin.study.kotlin.test.grammer

import kotlin.random.Random

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Int, val right: Int) : Expr

fun main() {
    println(eval(Sum(eval(Sum(1, 2)), eval(Num(3)))))
    forTest()
}

fun eval(e: Expr): Int {
    if (e is Num) {
        return e.value
    } else if (e is Sum) {
        return e.left + e.right
    } else {
        throw IllegalArgumentException("Unknown expression")
    }
}

fun eval2(e: Expr) =
    when (e) {
        is Num -> e.value
        is Sum -> e.left + e.right
        else -> throw java.lang.IllegalArgumentException("Unknown expression")
    }

fun forTest() {
    val testArray: ArrayList<Int> = ArrayList(10)
    //初始化一个数列
    for (i in 0 until 10) {
        testArray.add(Random.nextInt(0, 10))
    }
    println("init data: $testArray")

    //冒泡排序算法
    for ((index, _) in testArray.withIndex()) {
        for (j in 0 until testArray.size - 1 - index) {
            if (testArray[j] < testArray[j + 1]) {
                val temp = testArray[j]
                testArray[j] = testArray[j + 1]
                testArray[j + 1] = temp
            }
        }
    }

    println("sort data: $testArray")

    for (i in testArray.size-1 downTo 0 step 1){
        print(testArray[i])
        print(" ")
    }

}

