package com.slin.study.kotlin.samples.grammer

import com.slin.study.kotlin.samples.grammer.Color.*

/**
 * 1. enum class -> 声明一个枚举
 * 2. 与声明类一样，名称后面的括号里面可以跟属性
 * 3. 枚举里面可以声明方法
 */
enum class Color(private val r: Int, private val g: Int, private val b: Int) {
    RED(255, 0, 0),     //使用逗号分割
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238); //最后一行需要使用分号

    //可以定义方法
    fun rgb() = (r * 256 + g) * 256 + b

}

fun main() {
    println(BLUE.rgb())
    println(getMnemonic(VIOLET))
    println(getWarmth(RED))
    println(mix(RED, YELLOW))
    println(recognize('a'))
}

/**
 * 1. when 是一个带返回值的表达式，可以直接赋值给函数或者变量
 * 2. when 条件与表达式直接使用“->”连接
 */
fun getMnemonic(color: Color) =
    when (color) {
        RED -> "Richard"
        ORANGE -> "Of"
        YELLOW -> "York"
        GREEN -> "Gave"
        BLUE -> "Battle"
        INDIGO -> "In"
        VIOLET -> "Vain"
    }

/**
 * 1. 多个条件对应一个分支，可以使用“,”分割条件
 */
fun getWarmth(color: Color) =
    when (color) {
        RED, ORANGE, YELLOW -> "warm"
        GREEN -> "neutral"
        BLUE, INDIGO, VIOLET -> "cold"
    }

/**
 * 1. when 可以使用任意对象
 * 2. else 表示没有匹配到数据
 */
fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("dirty color")
    }

/**
 * 1. when 可以不用传实参，直接使用返回boolean值的表达式，并且可以使用与符号(“&&”)或者或符号(“||”)连接
 * 2. when可以完全替代if
 * 3.
 */
fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
        (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
        (c1 == BLUE && c2 == VIOLET) || (c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty color")
    }


/**
 * 1. when 的条件可以表示在某个范围内
 *
 */
fun recognize(c: Char) =
    when (c) {
        in 'a'..'z' -> "It's a digit!"
        in '0'..'9' -> "It's a letter!"
        else -> "I don't know.."
    }