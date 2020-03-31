package com.slin.study.kotlin.samples.annotation

import java.time.LocalDate
import java.time.Period


/**
 * author: slin
 * date: 2020-03-24
 * description:DSL
 * 1. 定义带接收者的lambda参数对象，`String.(Int,Int)->Unit`，String表示接收者对象
 * 2. `invoke`约定，invoke是一个操作符函数，实现之后就可以像函数一样可以调用的对象
 * 3. 在基础数据类型上面定义扩展函数
 */

fun main() {

    val table = createTable()
    table.maxClass = table.curClass
    println("create: \n$table")

    val bavarianGreeter = Greeter("Servus")
    //这里可以像函数调用一样调用对象，因为该对象实现了invoke操作符函数
    bavarianGreeter("Dmitry")

    val dependencies = DependencyHandler()
    dependencies.compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
    dependencies {
        compile("org.jetbrains.kotlin:kotlin-reflect:1.0.0")
        implements("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.0.0")
    }

    println(1.days.ago)
    println(1.days.fromNow)
}

/**
 * 定义带接收者的lambda参数对象
 */
fun slinBuildString(action: StringBuilder.() -> Unit): String {
    return StringBuilder().apply { action(this) }.toString()
}

open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()
    var curClass: Int = 0
        private set
    var maxClass: Int = 0
        set(value) {
            for (tag in children) {
                tag.maxClass = value
            }
            field = value
        }

    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        curClass = child.curClass + if (child.children.size > 0) 1 else 0 + 1
        children.add(child)
    }

    override fun toString(): String {
        println("name:$name curClass:$curClass")
        val classStr = buildString {
            repeat(maxClass - curClass) { append("\t") }
        }
        return slinBuildString {
            append(classStr).append("<").append(name).append(">")
            append(children.joinToString("\n", "\r\n", "\r\n"))
            append(classStr).append("</").append(name).append(">")
        }
    }
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE : Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)

}

class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}

class TD : Tag("td") {
    fun ts(init: TS.() -> Unit) = doInit(TS(), init)
}

class TS : Tag("ts")

fun createTable() =
    table {
        for (i in 1..2) {
            tr {
                td {
                    ts { }
                }
            }
        }
    }

/**
 * invoke约定，像函数一样可以调用的对象
 */
class Greeter(private val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }
}

/**
 * 使用带接收者的lambda参数对象和invoke约定来实现灵活的DSL语法
 */
class DependencyHandler {
    fun compile(coordinate: String) {
        println("Added dependency on $coordinate")
    }

    fun implements(coordinate: String) {
        println("Added implements on $coordinate")
    }

    operator fun invoke(body: DependencyHandler.() -> Unit) {
        body()
    }

}

/**
 * 基础数据类型上面定义扩展函数
 */
val Int.days: Period
    get() = Period.ofDays(this)

val Period.ago: LocalDate
    get() = LocalDate.now() - this

val Period.fromNow: LocalDate
    get() = LocalDate.now() + this


