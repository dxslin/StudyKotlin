package com.slin.study.kotlin.test.grammer

import kotlin.random.Random

//声明一个接口
interface Expr

//声明一个实现Expr的类
class Num(val value: Int) : Expr

//声明一个实现Expr的类
class Sum(val left: Int, val right: Int) : Expr

/**
 * 可以在外面定义变量
 */
var count = 0

fun main() {
    println(eval(Sum(eval(Sum(1, 2)), eval(Num(3)))))
    forTest()
    forMapTest()

    println(count++)
}

/**
 * 1. if的用法与Java一样
 * 2. if是一个表达式可以直接赋值给函数
 * 3. is 相当于 Java的 instanceof
 */
fun eval(e: Expr): Int =
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        e.left + e.right
    } else {
        throw IllegalArgumentException("Unknown expression")
    }


fun eval2(e: Expr) =
    when (e) {
        is Num -> e.value
        is Sum -> e.left + e.right
        else -> throw java.lang.IllegalArgumentException("Unknown expression")
    }

fun forTest() {
    println("for test")
    //“数字..数字”可以表示一个区间类型, step可以表示步长，step可以省略
    val range = 0..100 step 10
    //for通过in遍历整个区间
    for (i in range) {
        print("${i}, ")
    }
    println()
    //“数字 until 数字”也可以表示一个区间类型
    val range2 = 0 until 100 step 10
    //for通过in遍历整个区间
    for (i in range2) {
        print("${i}, ")
    }
    println()
    val testArray: ArrayList<Int> = ArrayList(10)
    // 初始化一个数列
    for (i in 0 until 10) {
        testArray.add(Random.nextInt(0, 10))
    }
    println("init data: $testArray")

    /**
     * 冒泡排序算法
     * 1. for((index, value) in array.withIndex())可以直接将数组的下标和值赋值到(index, value)里面
     *      ，如果不想使用index/value可以使用_代替
     * 2. 同理，for((key, value) in map)可以将map的key和value赋值到(key, value)中
     */
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

    print("back data: [")
    /**
     * 1. 下降区间可以使用 downTo 表示
     * 2. step 可以表示步长
     */
    for (i in testArray.size-1 downTo 0 step 1){
        print("${testArray[i]}, ")
    }
    println("]")
}

//测试打印map
fun forMapTest() {
    var testMap = hashMapOf(1 to "a", 2 to "b")
    for ((key, value) in testMap) {
        println("key: $key, value: $value")
    }
    if (1 !in testMap) {

    }
}

