package com.slin.study.kotlin.samples.lambdaEx

import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * author: slin
 * date: 2020-02-29
 * description: 高阶函数，即以另一个函数作为参数或者返回值的函数
 * 1. 函数类型，格式“(参数类型) -> 返回值类型”
 * 2. 定义参数为函数类型的函数
 * 3. 定义返回值为函数类型的函数
 * 4. 使用函数类型作为参数可以有效地避免很多重复代码
 * 5. 使用内联函数提升lambda运行效率
 * 6. return的作用域，return始终返回最近的一个由fun修饰的函数，可以使用`return@标签`来指定返回函数
 */

fun main() {
    /**
     * 函数类型，格式“(参数类型) -> 返回值类型”，
     * 小括号中填写参数值类型，多个用逗号`,`分割，后面紧跟`->`和返回值参数
     */
    val sum: (Int, Int) -> Int = { x, y -> x + y }
    //没有返回值，就是Unit
    val action: () -> Unit = { println("Hello Slin") }
    //返回值可为空的函数类型
    val canReturnNull: (Int, Int) -> Int? = { _, _ -> null }
    //可为空的函数类型
    val funOrNull: ((Int, Int) -> Int)? = null

    twoAndThree(sum)
    twoAndThree { x, y -> x * y }

    println("filter: ${"abc1d2fc3e".filter { c -> c in 'a'..'z' }}")

    val letters = listOf("Alpha", "Beta")
    println("joinToString: ${letters.joinToString(transform = { it.toLowerCase(Locale.ROOT) })}")
    println(
        "joinToString: ${letters.joinToString(
            separator = "!",
            transform = { it.toUpperCase(Locale.ROOT) })}"
    )

    println("joinToString: ${letters.joinToStringNull(separator = ". ")}")

    val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
    println("Shipping costs ${calculator(Order(3))}")

    val contacts = listOf(
        PersonPhone("Dmitry", "Jemerov", "123-4567"),
        PersonPhone("Alice", "Smith", "192-2893"),
        PersonPhone("Svetlana", "Isakova", null),
        PersonPhone("Tom", "Bafo", null)
    )
    val contactListFilters = ContactListFilters()
    with(contactListFilters) {
        prefix = "Dm"
        onlyWithPhoneNumber = true
    }
    println(contacts.filter(contactListFilters.getPredicate()))

    println("Windows平台访问平均时间：${averageDurationForOS(OS.WINDOWS)}")
    println("移动平台访问平均时间：${averageDurationForMobile()}")
    println("Linux平台访问index页面平均时间：${averageDurationFor { it.os == OS.LINUX && it.path == "/index" }}")
    println("访问login页面平均时间：${averageDurationFor { it.path == "/login" }}")

    //内联函数测试
    val lock = ReentrantLock()
    foo(lock)
    __foo__((lock))

    lock.withLock(action)

    //使用use来操作可关闭的资源，在使用完毕之后将自动关闭
//    BufferedReader(InputStreamReader(System.`in`)).use {
//        println(it.readLine())
//    }

    lookForAlice(contacts)
    lookForAliceReturnLabel(contacts)
    lookForAliceReturnFun(contacts)
    lookForAliceReturnAnonymousFun(contacts)
}
/*****************Start:定义使用lambda做参数的函数*************************************************/
/**
 * 定义使用lambda做参数的函数
 */
fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

/**
 * 实现一个简化版的filter
 */
fun String.filter(predicate: (Char) -> Boolean): String {
    val builder = StringBuilder()
    forEach { c ->
        if (predicate(c)) {
            builder.append(c)
        }
    }
    return builder.toString()
}

fun <T> Collection<T>.joinToString(
    separator: String = ",",
    prefix: String = "",
    postfix: String = "",
    transform: (T) -> String = { it.toString() }
): String {
    val sb = StringBuilder(prefix)
    for ((index, element) in withIndex()) {
        if (index > 0) sb.append(separator)
        sb.append(transform(element))
    }
    sb.append(postfix)
    return sb.toString()
}

/**
 * 使用可为空的函数类型作为参数
 */
fun <T> Collection<T>.joinToStringNull(
    separator: String = ",",
    prefix: String = "",
    postfix: String = "",
    transform: ((T) -> String)? = null
): String {
    val sb = StringBuilder(prefix)
    for ((index, element) in withIndex()) {
        if (index > 0) sb.append(separator)
        //函数类型为空时调用使用`?.invoke`来调用，这样可以避免不为空检查
        val str = transform?.invoke(element) ?: element.toString()
        sb.append(str)
    }
    sb.append(postfix)
    return sb.toString()
}

/*******************End:定义使用lambda做参数的函数*************************************************/

/*****************Start:定义lambda为返回值的函数***************************************************/

enum class Delivery { STANDARD, EXPEDITED }

class Order(val itemCount: Int)

/**
 * 定义返回值为函数类型的函数
 */
fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double {
    if (delivery == Delivery.EXPEDITED) {
        return { order -> 6 + 2.1 * order.itemCount }
    }
    return { order -> 1.2 * order.itemCount }
}

data class PersonPhone(val firstName: String, val lastName: String, val phoneNumber: String?)
class ContactListFilters {
    var prefix = ""
    var onlyWithPhoneNumber: Boolean = false

    /**
     * 返回一个函数类型的表达式
     */
    fun getPredicate(): (PersonPhone) -> Boolean {
        val startWithPrefix = { p: PersonPhone ->
            p.firstName.startsWith(prefix) || p.lastName.startsWith(prefix)
        }
        if (!onlyWithPhoneNumber) {
            return startWithPrefix
        }
        return { startWithPrefix(it) && it.phoneNumber != null }
    }

}
/*******************End:定义lambda为返回值的函数***************************************************/

/*****************Start:使用lambda来去除重复代码***************************************************/

/**
 * 模仿一个网站访问记录统计
 */
data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }

/**
 * 随便添加一些数据
 */
val visitLog = listOf(
    SiteVisit("/", 12.5, OS.WINDOWS),
    SiteVisit("/", 8.2, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 18.4, OS.ANDROID),
    SiteVisit("/", 5.0, OS.IOS),
    SiteVisit("/index", 6.6, OS.LINUX)
)

/**
 * 正常想统计不同的系统使用平均时间，那么需要添加一个函数来计算
 */
fun averageDurationForOS(os: OS): Double =
    visitLog.filter { it.os == os }.map(SiteVisit::duration).average()

/**
 * 但是如果想要统计移动平台的访问时间就需要再写一个函数了，
 */
fun averageDurationForMobile(): Double =
    visitLog.filter { it.os in setOf(OS.ANDROID, OS.IOS) }.map(SiteVisit::duration).average()

/**
 * 如果现在又想添加其他条件，又需要重写一段代码，使用lambda作为参数可以很好的规避这个问题
 */
fun averageDurationFor(predicate: (SiteVisit) -> Boolean): Double =
    visitLog.filter(predicate).map(SiteVisit::duration).average()


/*******************End:使用lambda来去除重复代码***************************************************/

/*****************Start:内联函数*******************************************************************/
/**
 * 定义一个内联函数，使用`inline`关键字声明即可
 */
inline fun <T> synchronized(lock: Lock, action: () -> T): T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

/**
 * 使用内联函数提升lambda运行效率
 * 原理：kotlin编译为字节码时将lambda代码块替换到函数内部
 * 例如下面foo函数使用了synchronized方法，那么编译时会编译成__foo__函数形式
 * 如果函数很大，不建议使用内联函数，因为它将极大的增加代码量
 */
fun foo(lock: Lock) {
    println("Before sync")
    synchronized(lock) {
        println("Action")
    }
    println("After sync")
}

fun __foo__(lock: Lock) {
    println("Before sync")
    lock.lock()
    try {
        println("Action")
    } finally {
        lock.unlock()
    }
    println("After sync")
}
/*******************End:内联函数*******************************************************************/

/*****************Start:高阶函数的控制流***********************************************************/

/**
 * 非局部返回
 */
fun lookForAlice(persons: List<PersonPhone>) {
    var index = 0
    persons.forEach {
        if (it.firstName == "Alice") {
            println("lookForAlice: found Alice")
            /**
             * lambda里面的return会直接将整个函数结束掉
             */
            return
        }
        println("lookForAlice: index: ${index++}")
    }
    println("lookForAlice: Alice is not found")
}

/**
 * 使用`return@标签`来返回
 */
fun lookForAliceReturnLabel(persons: List<PersonPhone>) {
    var index = 0
    persons.forEach label@{
        println("lookForAliceReturnLabel before return: index: $index")
        if (it.firstName == "Alice") {
            println("lookForAliceReturnLabel: found Alice")
            /**
             * 这里使用return标签，那么只会返回forEach函数
             */
            return@label
        }
        println("lookForAliceReturnLabel after return: index: ${index++}")
    }
    println("lookForAliceReturnLabel: Alice might be somewhere")
}

/**
 * 使用`return@函数名`来返回
 */
fun lookForAliceReturnFun(persons: List<PersonPhone>) {
    var index = 0
    persons.forEach {
        println("lookForAliceReturnFun before return: index: $index")
        if (it.firstName == "Alice") {
            println("lookForAliceReturnFun: found Alice")
            /**
             * 这里可以用函数名代替标签，当自定义标签后，函数名不能代替
             */
            return@forEach
        }
        println("lookForAliceReturnFun after return: index: ${index++}")
    }
    println("lookForAliceReturnFun: Alice might be somewhere")
}

/**
 * 匿名函数返回
 */
fun lookForAliceReturnAnonymousFun(persons: List<PersonPhone>) {
    persons.forEach(fun(person) {
        if (person.firstName == "Alice") {
            println("found Alice")
            /**
             * return 只能从最近的fun关键字声明的函数返回，因此这里只能返回匿名函数
             */
            return
        }
        println("${person.firstName} is not Alice")
    })
    println("lookForAliceReturnAnonymousFun: Alice might be somewhere")
}
