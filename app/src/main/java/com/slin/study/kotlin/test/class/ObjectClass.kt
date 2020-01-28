package com.slin.study.kotlin.test.`class`

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
     * object关键字声明匿名对象取代Java的匿名内部类
     * 匿名对象每次声明都是创建一个新的实例
     */
    person3.setOnSalaryChangeListener(object : Person.OnSalaryChangeListener {
        override fun onNameChange(oldName: Int, newName: Int) {
            println("${person3.name}'s age change to $newName from $oldName")
        }
    })

    person3.salary = 18

}

/**
 * 使用object关键字定义伴生对象，单例模式
 * 1. 使用object而非class关键字
 * 2. 不能有构造函数
 * 3. 其他与类定义一样，可以有属性，方法，初始化语句等
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

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person(val name: String, salary: Int = 0) {

    /**
     * 嵌套类实现伴生对象
     */
    object NameComparator : Comparator<Person> {
        override fun compare(o1: Person, o2: Person): Int {
            return o1.name.compareTo(o2.name)
        }
    }

    /**
     * 普通对象使用伴生对象，实现工厂方法
     * 1. 使用 companion object 关键字定义伴生对象
     * 2. 如果不分配名字的话会默认使用“Companion”
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