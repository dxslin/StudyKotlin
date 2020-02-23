package com.slin.study.kotlin.test.operator

import java.math.BigDecimal

/**
 * author: slin
 * date: 2020-02-23
 * description: 重载运算符
 *
 * 重载运算符
 * 1. 使用关键字“operator”修饰并重写运算符对应的方法即可
 * 2. 运算符与函数名对应表格如下，如果是“+=”函数名为“对应函数名+Assign”，如“+=”函数名为“plusAssign”
 *      表达式             函数名
 *      a * b               times
 *      a / b               div
 *      a % b               mod
 *      a + b               plus
 *      a - b               minus
 *
 * 3. *、/和%具有相同的优先级，且优先+和-
 * 4. 也可以把运算符定义为扩展函数
 * 5. 运算数类型不同时，不符合交换律
 * 6. kotlin不支持标准数字类型的位运算符，但是提供了支持中缀调用的函数
 *      函数名                     含义
 *      shl                     带符号左移
 *      shr                     带符号左移
 *      ushr                    无符号右移
 *      and                     按位与
 *      or                      按位或
 *      xor                     按位异或
 *      inv                     按位取反
 * 7. 重载复合运算符：重写plus和plusAssign都可以使用+=，如果两个都重写了且对象是var时（val会调用plusAssign）使用+=会报错，
 *      因此建议只重写一个。如果对象属性是可变的，建议重写plusAssign（其返回值为Unit），改变对象内容；
 *      如果对象属性是不可变的则重写plus
 * 8. 重载一元运算符，方法同上，只是对应方法无参数，对应方法如下
 *      表达式                 函数名
 *      +a                      unaryPlus
 *      -a                      unaryMinus
 *      !a                      not
 *      ++a, a++                inc
 *      --a, a--                dec
 *
 */

fun main() {

    /******************运算符重载****************************************/
    var point1 = Point(1, 3)
    val point2 = Point(2, 4)
    println("point1 + point2: ${point1 + point2}")
    println("point1 * 1.5: ${point1 * 1.5}")
    println("'a'.times(5): ${'a'.times(5)}")
    println("'a'.times('a'): ${'a'.times('a')}")

    /******************位运算符示例**************************************/
    println("\n位运算符示例")
    println("0x0F and 0xF0: ${0x0F and 0xF0}")
    println("0x0F or 0xF0: ${0xF0 or 0x0F}")
    println("0x01 shl 4: ${0x01 shl 4}")

    /******************复合运算符重载************************************/
    println("\n复合运算符重载")
    var point3 = Point(10, 20)
    point3 += point2
    println("point3 += point2 result: $point3")
    val numbers = ArrayList<Int>()
    //+=只是修改了list的内容，并没有返回新的引用
    numbers += 1
    println("numbers[0]: ${numbers[0]}")
    //+返回了一个新的引用
    val numbers2 = numbers + listOf(2, 3)
    println("numbers2: $numbers2")
    val rect1 = Rectangle(point1, point3)
    val rect2 = Rectangle(point1, point2)
    println("rect1: $rect1")
    println("rect2: $rect2")
    rect1 += rect2
    println("rect1 += rect2: $rect1")

    /******************一元运算符重载************************************/
    println("\n一元运算符重载")
    println("-point1: ${-point1}")
    println("point1++: ${++point1}")
    var bigDecimal = BigDecimal(10)
    println("--bigDecimal: ${--bigDecimal}")


    /******************比较运算符重载************************************/
    println("\n比较运算符重载")
    println("rect1 == rect2: ${rect1 == rect2}")
    println("rect1 != rect2: ${rect1 != rect2}")
    println("null != rect1: ${rect1 == null}")

    println("point1 > point2: ${point1 > point2}")
    println("rect1 >= rect2: ${rect1 >= rect2}")
    println(
        """Person("Alice", "Swith") > Person("Bob", "Johnson"): ${Person(
            "Alice",
            "Swith"
        ) > Person("Bob", "Johnson")}"""
    )

}


data class Point(val x: Int, val y: Int) : Comparable<Point> {

    /**
     * 重载加法运算符
     * 1. 使用关键字“operator”修饰方法
     * 2. plus函数名对应加法，对应表格如下
     *  表达式             函数名
     *  a * b               times
     *  a / b               div
     *  a % b               mod
     *  a + b               plus
     *  a - b               minus
     * 3. *、/和%具有相同的优先级，且优先+和-
     */
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    override fun compareTo(other: Point): Int {
        return compareValuesBy(this, other, {
            it.x * it.x + it.y * it.y
        })
    }

}

/**
 * 1. 扩展函数也可以重载运算符
 * 2. 运算数类型不同时，不符合交换律，比如这里只能“point * 1.5”，不能使用“1.5 * point”
 */
operator fun Point.times(scale: Double): Point {
    return Point((x * scale).toInt(), (y + scale).toInt())
}

/**
 * 定义一个返回类型不同的运算符
 */
operator fun Char.times(count: Int): String {
    return toString().repeat(count)
}

/**
 * 与普通方法一样，运算符方法可以重载
 */
operator fun Char.times(count: Char): String {
    return toString().repeat(count.toInt())
}

class Rectangle(var topLeft: Point, var bottomRight: Point) : Comparable<Rectangle> {

    override fun toString(): String {
        return "Rectangle((${topLeft.x}, ${topLeft.y}), (${bottomRight.x}, ${bottomRight.y}))"
    }

    /**
     * 重写复合运算符 +=
     * 1. 返回值为必须空(Unit)，即不会改变引用，只会改变内容
     *
     */
    operator fun plusAssign(rect: Rectangle) {
        topLeft = Point(topLeft.x + rect.topLeft.x, topLeft.y + rect.topLeft.y)
        bottomRight = Point(bottomRight.x + rect.bottomRight.x, bottomRight.y + rect.bottomRight.y)
    }

    /**
     * 重载等号运算符 equals
     * 1. 只需要重写equals方法即可
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rectangle) return false
        return topLeft == other.topLeft && bottomRight == other.bottomRight
    }

    override fun hashCode(): Int {
        return topLeft.hashCode() * 31 + bottomRight.hashCode()
    }

    /**
     * 重载比较运算符
     * 1. 继承Comparable接口，并实现即可
     */
    override fun compareTo(other: Rectangle): Int {
        return compareValuesBy(this, other, {
            it.bottomRight.x - it.topLeft.x
        }, {
            it.bottomRight.y - it.topLeft.y
        })
    }

}

/**
 * 重载一元运算符
 */
operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}

operator fun Point.inc(): Point {
    return Point(x + 1, y + 1)
}


class Person(val firstName: String, val lastName: String) : Comparable<Person> {

    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, {
            it.firstName
        }, {
            it.lastName
        })
    }

}