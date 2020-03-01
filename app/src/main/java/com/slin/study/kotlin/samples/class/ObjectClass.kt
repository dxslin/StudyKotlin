package com.slin.study.kotlin.samples.`class`

import com.google.gson.Gson


/**
 * author: slin
 * date: 2020-01-27
 * description:单例模式和伴生对象
 */

fun main() {
    val employees = listOf(
        Person("tom", 7000),
        Person("jay", 10000), Person("slin", 12000)
    )
    Payroll.allEmployees.addAll(employees)
    println("Payroll.calculateSalary(): ${Payroll.calculateSalary()}")

    println(
        "employees.sortWith(Person.NameComparator): " +
                "${employees.sortedWith(Person.NameComparator)}"
    )

    val customer11 = Customer1("slin@qq.com")
    val customer12 = Customer1(10086)
    println("customer11: ${customer11.nickname}")
    println("customer11: ${customer12.nickname}")

    //伴生对象 工厂方法来替代构造函数
    val customer21 = Customer2.newSubscribingCustomer("slin@qq.com")
    val customer22 = Customer2.newFacebookCustomer(10086)
    println("customer21: ${customer21.nickname}")
    println("customer22: ${customer22.nickname}")

    val jsonText = """{"name":"slin"}"""
    //可以省略伴生对象的名称，即Loader
//    val person = Person.Loader.fromJSON(jsonText)
    val person = Person.fromJSON(jsonText)
    println("person: $person")

    val person2 = loadFromJSON(Person.Loader, jsonText)
    println("person2: $person2")

    //可以省略伴生对象的名称，即Loader
//    val person3 = Person.Loader.fromName("haha")
    val person3 = Person.fromName("haha")
    println("person3: $person3")

    /**
     * object关键字声明匿名对象(对象表达式)取代Java的匿名内部类
     * 1. 匿名对象每次声明都是创建一个新的实例，非单例
     * 2. 匿名对象可以实现多个接口
     */
    person3.setOnSalaryChangeListener(object : Person.OnSalaryChangeListener, Runnable {
        override fun onNameChange(oldName: Int, newName: Int) {
            println("${person3.name}'s age change to $newName from $oldName")
        }

        override fun run() {

        }
    })

    person3.salary = 18

}

/**
 * 使用object关键字对象声明，单例模式
 * 1. 对象声明使用object关键字
 * 2. 不能有构造函数
 * 3. 其他与类定义一样，可以有属性，方法，初始化语句，继承等
 */
object Payroll {

    val allEmployees = arrayListOf<Person>()

    fun calculateSalary(): Int {
        var total = 0
        for (person in allEmployees) {
            total += person.salary
        }
        return total
    }

}


class Customer1 {
    val nickname: String

    constructor(email: String) {
        nickname = email.substringBefore('@')
    }

    constructor(facebookAccountId: Int) {
        nickname = getFacebookName(facebookAccountId)
    }

}

/**
 * 定义伴生对象，使用工厂方法提嗲构造函数
 *
 */
class Customer2(val nickname: String) {

    /**
     * 伴生对象
     * 1. 使用 companion object关键字定义伴生对象
     * 2. 相当于Java里面静态方法(static修饰的方法)，kotlin没有static关键字
     * 3. 半生对象的名称可以省略，省略后默认为“Companion”
     */
    companion object {
        fun newSubscribingCustomer(email: String) =
            Customer2(email.substringBefore('@'))

        fun newFacebookCustomer(accountId: Int) =
            Customer2(getFacebookName(accountId))

    }

}


interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person(val name: String, salary: Int = 0) {

    /**
     * 嵌套类对象声明，单例对象
     * 1. 在任何地方都可以定义和使用单例对象
     */
    object NameComparator : Comparator<Person> {
        override fun compare(o1: Person, o2: Person): Int {
            return o1.name.compareTo(o2.name)
        }
    }

    /**
     * 普通对象使用伴生对象
     * 3. 伴生对象可以实现接口
     */
    companion object Loader : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person {
            return Gson().fromJson(jsonText, Person::class.java)
        }
    }

    fun setOnSalaryChangeListener(onSalaryChangeListener: OnSalaryChangeListener) {
        this.onSalaryChangeListener = onSalaryChangeListener
    }

    interface OnSalaryChangeListener {
        fun onNameChange(oldName: Int, newName: Int)
    }

    private var onSalaryChangeListener: OnSalaryChangeListener? = null

    var salary: Int = salary
        set(value) {
            onSalaryChangeListener?.onNameChange(field, value)
            field = value
        }

    override fun toString(): String {
        return """Person(name=$name, salary=$salary)"""
    }
}

fun <T> loadFromJSON(factory: JSONFactory<T>, jsonText: String): T {
    return factory.fromJSON(jsonText)
}

/**
 * 为伴生对象定义一个扩展函数
 */
fun Person.Loader.fromName(name: String): Person {
    return Person(name)
}

fun getFacebookName(facebookAccountId: Int): String {
    return "facebookId-$facebookAccountId"
}