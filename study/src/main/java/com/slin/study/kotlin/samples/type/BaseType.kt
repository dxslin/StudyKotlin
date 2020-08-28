@file:Suppress("unused")

package com.slin.study.kotlin.samples.type

/**
 * author: slin
 * date: 2020-02-12
 * description: 基础数据类型和其他基础类型
 * 1. kotlin不区分基础数据类型和包装类型，由编译器自动决定使用基础数据类型还是包装类型（使用泛型时）
 * 2. 可空基本数据类型会编译成包装类型，因为基本数据类型不能使用null
 * 3. kotlin不会自动转换数字类，除非使用字面值进行赋值、传参或进行算式时
 * 4. Any相当于Java的Object，是非空类型的超类型，且又toString、hashCode()和equal()方法，但是不能使用wait()和notify()等Java方法
 * 5. Unit相当于Java的void，但是Unit可以用于泛型时使用
 * 6. Nothing表示返回值没有任何意义，因为根本不会成功的返回值
 */

fun main() {
    //1. kotlin不区分基础数据类型和包装类型，由编译器自动决定使用基础数据类型还是包装类型（使用泛型时）
    val index: Int = 1
    val list = listOf(1, 2, 3)

    showProgress(-1)
    showProgress(50)
    showProgress(100)
    showProgress(1000)

    val longList = listOf(1L, 2L, 3L)
    //kotlin的数据类型不会自动转换为范围更大的数据类型，必须要显示的转换
    //不能自动转换，下面编译不通过
//    val longNum:Long = index
//    println(index in longList)
    println(index.toLong() in longList)

    //使用字面值赋值或者传参时会自动转换，算式运算符被重载了也能自动转换
    val byteNum: Byte = 1
    val longNum2 = byteNum + 1L
    foo(24)

    /**
     * Any相当于Java的Object，是非空类型的超类型，且又toString、hashCode()和equal()方法，但是不能使用wait()和notify()等Java方法
     *
     */
    //这里数字会自动装箱
    val anyNum: Any = 24
    println("anyNum: $anyNum")
//    anyNum.wait()

    val person = AgePerson("Tom", 10)
    println("Tom is older than Lilo: ${person.isOlderThan(AgePerson("Lilo", 12))}")
    println("Tom is older than NoAge: ${person.isOlderThan(AgePerson("NoAge", null))}")

    val noResultProcessor = NoResultProcessor()
    println("process: ${noResultProcessor.process()}")


    val company = Company("JetBrains", null)
    val address = company.address ?: fail("No Address")
    println("address:$address")
}

fun showProgress(progress: Int) {
    // 可以针对一个数字类型调用函数
    val percent = progress.coerceIn(0, 100)
    println("We're ${percent}% done!")
}

/**
 * 可空基本数据类型是包装类型
 */
data class AgePerson(val name: String, val age: Int?) {
    fun isOlderThan(other: AgePerson): Boolean? {
        //因为age可能为空，所以必须检查不为空之后菜呢个进行比较
        if (age == null || other.age == null) {
            return null
        }
        return age > other.age
    }
}

fun foo(num: Long) {
    println(num)
}


interface Processor<T> {
    fun process(): T
}

/**
 * Unit相当于Java的void，但是Unit可以用于泛型时使用
 */
class NoResultProcessor : Processor<Unit> {
    //可以省略返回类型Unit
    override fun process(): Unit {
        //不用返回值
    }

    fun returnVoid(): Void? {
        return null
    }

}

/**
 * Nothing表示返回值没有任何意义，因为根本不会成功的返回值
 */
fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}