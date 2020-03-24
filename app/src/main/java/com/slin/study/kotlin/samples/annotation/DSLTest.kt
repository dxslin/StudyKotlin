package com.slin.study.kotlin.samples.annotation

/**
 * author: slin
 * date: 2020-03-24
 * description:
 */

fun main() {

    val table = createTable()
    table.maxClass = table.curClass
    println("create: \n$table")

}

fun slinBuildString(sb: StringBuilder.() -> Unit): String {
    return StringBuilder().apply { sb(this) }.toString()
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
