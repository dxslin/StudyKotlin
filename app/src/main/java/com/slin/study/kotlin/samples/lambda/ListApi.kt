package com.slin.study.kotlin.samples.lambda

import android.content.Context
import android.widget.TextView
import com.slin.study.kotlin.samples.`class`.Person
import com.slin.study.kotlin.support.TestSupport.postponeComputation
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * author: slin
 * date: 2020-02-02
 * description: 集合的函数式编程
 *
 */

fun main() {
    val list = listOf(1, 2, 3, 4)
    val employees = listOf(
        Person("tom", 7000), Person("jay", 10000),
        Person("slin", 12000), Person("ruse", 6000),
        Person("peter", 6000), Person("lon", 10000)
    )
    val listOfList = listOf(
        1..3,
        4..7,
        9..11
    )

    //filter 过滤无用元素，形成新的集合
    println("能被2整除的元素：" + list.filter { it % 2 == 0 })
    println("工资操作8000的员工姓名：" + employees.filter { it.salary > 8000 })

    //map 对每一个元素做一定操作后形成新的集合
    println("每一个元素的平方：" + list.map { it * it })
    println("所有的员工：" + employees.map { it.name })
    println("所有的员工：" + employees.map(Person::name))

    //组合使用 打印工资操作8000的员工姓名
    println("工资操作8000的员工姓名：" + employees.filter { it.salary > 8000 }.map(Person::name))

    //组合使用 这里每次都会重新调用maxBy计算最大值，后面为优化写法
    //记住lambda提供了简便写法，但是请牢记你的代码在干什么
    println("工资最高的员工姓名：" + employees.filter { it.salary == employees.maxBy(Person::salary)?.salary ?: 0 })
    val maxSalary = employees.maxBy(Person::salary)?.salary
    println("工资最高的员工姓名：${employees.filter { it.salary == maxSalary }}")

    //mapValues
    val numbers = mapOf(1 to "one", 2 to "two")
    println("转换为大写：${numbers.mapValues { it.value.toUpperCase(Locale.getDefault()) }}")

    //all & any 互为逆运算
    println("是否所以所有数字都大于2：${list.all { it > 2 }}")
    println("是否存在工资大于10000的员工：${employees.any { it.salary > 10000 }}")
    //取反 不推荐用取反的形式，建议直接用any
    println("至少有一个元素不等于3：${!list.all { it == 3 }}")

    //size & count count只统计数量，较之size有一定的速度优势
    println("工资大于8000的员工数：${employees.filter { it.salary > 8000 }.size}")
    println("工资大于8000的员工数：${employees.count { it.salary > 8000 }}")

    //find 寻找第一个匹配项，找不到则返回null
    println("工资等于10000的第一个员工姓名： ${employees.find { it.salary == 10000 }?.name}")

    //groupBy 分组，toSortMap 按key排序
    println("按工资分组：${employees.groupBy(Person::salary).toSortedMap()}}")

    //flatMap & flatten
    val strings = listOf("abc", "def")
    println("展开字符串：${strings.flatMap { it.toList() }}")

    val books = listOf(
        Book("Thursday Next", listOf("Jasper Fforde")),
        Book("Mort ", listOf("Terry Pratchett ")),
        Book("Good Omen", listOf("Terry Pratchett ", " Neil Gaiman"))
    )
    println("所有的作者：${books.flatMap { it.authors }.toSet()}")
    println("列表装列表直接展开：${listOfList.flatten()}")

    //asSequence 序列 避免创建大量中间元素
    val sListPeople = employees.asSequence()
        .map(Person::name)
        .filter { it.startsWith("s") }
        .toList()
    println("名字为s开头的员工：${sListPeople}")
    //序列的中间操作是惰性的，末端操作才会触发
    list.asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it > 4 }
        .toList()
    //先filter再map可以减少变换总次数
    employees.asSequence().map(Person::name).filter { it.length > 3 }.toList()
    employees.asSequence().filter { it.name.length > 3 }.map(Person::name)
    println()

    //生成序列 generateSequence函数可以生成一个序列
    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println("0到100的和：${numbersTo100.sum()}")

    fun File.isInsideHiddenDirectory() =
        generateSequence(this) { it.parentFile }.any(File::isHidden)

    val file = File(".idea/vcs.xml")
    println("文件是否在隐藏文件夹下： ${file.isInsideHiddenDirectory()}")

    //使用Java的函数式接口
    //使用匿名对象，每次都会创建新的对象
    postponeComputation(1000, object : Runnable {
        override fun run() {
            println("100")
        }
    })
    //使用lambda表达式，如果不引用来自自定义他的函数的变量，则只会创建一个
    postponeComputation(1200) { println("120") }

    println(alphabet())
    println(alphabetWith())
    println(alphabetWithSimple())
    println(alphabetApply())
    println(alphabetStringBuilder())

}


fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'a'..'z') {
        result.append(result)
    }
    result.append("\nNow I know the alphabet")
    return result.toString()
}

/**
 * with函数的使用，可以避免重复写同一个对象多次
 * 1. with函数的lambda表达式里面可以使用this访问with第一个参数的对象，也可以省略this
 * 2. 最后一行输出结果作为返回值
 */
fun alphabetWith(): String {
    val result = StringBuilder()
    return with(result) {
        for (letter in 'a'..'z') {
            //使用this访问result
            this.append(letter)
        }
        //省略this，直接调用result的公告方法
        append("\nNow I know the alphabet")
        //最后一行输出结果作为返回值
        this.toString()
    }
}

/**
 * with函数
 * 简写
 */
fun alphabetWithSimple(): String =
    with(StringBuilder()) {
        for (letter in 'a'..'z') {
            append(letter)
        }
        append("\nNow I know the alphabet")
        toString()
    }

/**
 * apply函数，
 * 1. apply使用方法与with基本一致
 * 2. 与with不同的是，apply返回的对象式调用者本身
 */
fun alphabetApply(): String =
    StringBuilder().apply {
        for (letter in 'a'..'z') {
            append(letter)
        }
        append("\nNoe I know the alphabet")
    }.toString()

/**
 * 使用apply来构建对象
 */
fun createTextView(context: Context): TextView {
    return TextView(context).apply {
        text = "apply test"
        textSize = 17.0f
        setPadding(10, 10, 10, 10)
    }
}

/**
 * buildString函数
 * 1.buildString会调用StringBuilder来构建String，并且自动调用toString()方法
 */
fun alphabetStringBuilder(): String =
    buildString {
        for (letter in 'a'..'z') {
            append(letter)
        }
        append("\nNow I know the alphabet")
    }

data class Book(val name: String, val authors: List<String> = ArrayList())

