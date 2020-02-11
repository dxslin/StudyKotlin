@file:Suppress("IfThenToElvis", "UNNECESSARY_SAFE_CALL", "unused", "DefaultLocale")

package com.slin.study.kotlin.test.type

import com.slin.study.kotlin.support.JavaPerson
import com.slin.study.kotlin.support.StringProcessor
import java.util.*

/**
 * author: slin
 * date: 2020-02-11
 * description:可空性
 *
 */

fun main() {
    //只有类型后面跟了“?”的才可以存储null
    val x: String? = null
    println("x length: ${strLengthSafe(x)}")

    /**
     * 安全运算符“?.”
     * 1. 只会调用非空值的方法和属性，不会报NullException，返回null
     * 2. 安全运算符可以多个链式调用，其中一个为空，后面返回值为null
     */
    x?.length
    val xStringLength = x?.toUpperCase(Locale.getDefault())?.length
    println("xStringLength: $xStringLength")

    /**
     * elvis运算符“?:”
     * 1. 如果elvis运算符前面的值返回空的话，则结果为符号后面的值，如果不为空则为前面的值
     */
    println("x length: ${x?.length ?: 0}")

    val address = Address("Elsestr 47", 80687, "Munich", "Germany")
    val jetbrains = Company("JetBrains", address)
    val dmitry = Person("Dmitry", jetbrains)
    val alexey = Person("Alexey", null)

    printShippingLabel(dmitry)
    //运行这里会抛出异常 throw IllegalArgumentException("No Address")
//    printShippingLabel(alexey)
    //运行这里会抛出异常
//    ignoreNull(null)

    /**
     * let 函数
     * 1. 只有email不为空时才会执行后面lambda表达式
     */
    var email: String? = null
    email?.let {
        sendEmailTo(it)
    }
    email = "dxslin@qq.com"
    email?.let { sendEmailTo(it) }

    //延迟初始化测试
    val lateInitTest = LateInitTest()
    lateInitTest.lateInit()
    lateInitTest.testFun()

    //可空类型的扩展 测试
    println("Non".isEmptyOrNon())
    println(x?.isEmpty())

    printHashCode(null)

    val javaPerson = JavaPerson(null)
    //这里会报异常
//    yellAt(javaPerson)
    yellAtSafe(javaPerson)
}

/**
 * 测试符号：?
 *
 */
fun strLengthSafe(str: String?) =
    if (str == null) 0 else str.length

/**
 * 测试符号：?: Elvis运算符（null合并运算符）
 * 同时使用Elvis运算符和throw
 */
fun printShippingLabel(person: Person) {
    val address = person.company?.address ?: throw IllegalArgumentException("No Address")
    address.apply {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

/**
 * 测试符号：as?     安全转换符
 * 安全转换符号“as?”可以尝试转换未给定的类型，如果类型不合适就返回null
 */
class People(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        val otherPeople = other as? People ?: return false
        return otherPeople.firstName == firstName && otherPeople.lastName == lastName
    }
}

/**
 * 测试符号：!!  非空断言
 * 使用非空断言“!!”后，编译器将不再认为该值是可为空的，如果为空，则会报空指针异常
 */
fun ignoreNull(s: String?) {
    val sNotNull = s!!
    println(sNotNull.length)
}

fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

/**
 * 延迟初始化属性
 * 1. 使用“lateinit var”定义属性
 * 2. 属性调用时不需要使用“?.”来调用
 * 3. 自己需要明白什么时候初始化的对象，确保调用时属下一定被初始化了
 */

class MyService {
    fun performAction(): String = "foo"
}

class LateInitTest {
    private lateinit var myService: MyService

    fun lateInit() {
        myService = MyService()
    }

    fun testFun() {
        println("foo==performAction? --> ${"foo".equals(myService.performAction())}")
    }
}

/**
 * 可空类型的扩展函数
 * 1. 可以使用“this == null”判断对象是否为空，判断后后面的对象会自动转换为非空对象
 *
 */
fun String?.isEmptyOrNon(): Boolean {
    return this == null || this.isEmpty() || this.toLowerCase(Locale.getDefault()) == "non"
}

/**
 * 泛型时，T不写上界表示Any?类型，是可以为null的，如果不想参数可以为空，可以写作<T : Any>限制上界
 */
fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}

/**
 * Java返回的值，如果没有@Nullable和@NotNull注解的话，kotlin是无法判断值是否为空，需要自己做安全检查
 */
fun yellAt(javaPerson: JavaPerson) {
    println(javaPerson.name.toUpperCase())
}

fun yellAtSafe(javaPerson: JavaPerson) {
    println((javaPerson.name ?: "Anyone").toUpperCase() + "!!!")
}


/**
 * kotlin实现类和接口时一定要弄清楚它的可空性，kotlin编译器会为声明的每一个非空参数增加一个非空的断言
 */
class StringPrinter : StringProcessor {
    override fun process(value: String) {
        println(value)
    }
}

class NullableStringPrinter : StringProcessor {
    override fun process(value: String?) {
        value?.let { println(it) }
    }
}

class Address(val streetAddress: String, val zipCode: Int, val city: String, val country: String)
class Company(val name: String, val address: Address?)
class Person(val name: String, val company: Company?)


