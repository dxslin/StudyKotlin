package com.slin.study.kotlin.test.operator

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * author: slin
 * date: 2020-02-29
 * description: 委托属性，把自己的工作委托给另外一个辅助对象去实现
 * 1. 基本语法，在类型后面使用关键字by指出辅助对象
 * <code>
 *     class Foo{
 *          var prop: Type by Delegate()
 *     }
 * </code>
 * 2. 被委托的对象必须具有`getValue`和`setValue`操作符函数，也即实现'ReadWriteProperty'接口
 * 3. 实现原理，编译器会自动为委托对象添加一个隐藏的属性，然后访问委托对象时就会调用隐藏属性的`getValue`和`setValue`方法
 * <code>
 *     class Foo{
 *          private val <delegate> = MyDelegate()
 *          var prop:Type
 *              get() = <delegate>.getValue(this, <property>)
 *              set() = <delegate>.setValue(this, <property>, value)
 *     }
 *     调用时相当于：
 *     val x = foo.prop     ==>     val x = <delegate>.getValue(foo, <property>)
 *     foo.prop = x         ==>     <delegate>.setValue(foo, <property>, value)
 * 4. 四种属性委托的应用场景：
 *      1). 惰性初始化，lazy函数实现惰性初始化的原理就是类型委托
 *      2). 监听属性变化，可以在修改属性的时候插入变化的监听器
 *      3). 在动态定义属性集的对象中使用
 *      4). 在数据库表中使用，这里涉及到的比较少
 */
fun main() {
    val people1 = People1("Alice")
    println(people1.emails)
    println(people1.emails)
    val people2 = People2("Tom")
    println(people2.emails)
    println(people2.emails)

    val peopleChangeAware = PeopleChangeAware("June", 18, 8000)
    peopleChangeAware.addPropertyChangeListener(PropertyChangeListener { event ->
        println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
    })
    peopleChangeAware.age = 20
    peopleChangeAware.salary = 10000

    val peopleDelegateObservable = PeopleDelegateObservable("Slin", 25, 8000)
    peopleDelegateObservable.addPropertyChangeListener(PropertyChangeListener { evt ->
        println("Property ${evt.propertyName} changed form ${evt.oldValue} to ${evt.newValue}")
    })
    peopleDelegateObservable.age = 26
    peopleDelegateObservable.salary = 12000

    val peopleMap = PeopleMap();
    peopleMap.setAttribute("firstName", "King")
    peopleMap.setAttribute("lastName", "Smith")
    println(peopleMap.firstName)
    println(peopleMap.lastName)

}

data class Email(val content: String)
open class People(open val name: String)

fun loadEmails(people: People): List<Email> {
    println("load emails for ${people.name}")
    return listOf(Email("hello"), Email("thanks"))
}

/**
 * 使用支持属性来实现惰性初始化，自己提供了一个_emails来储存数据，通过emails来访问，
 * 这种方式比较麻烦，且非线程安全，因此kotlin提供了更简单的方式
 */
class People1(override val name: String) : People(name) {
    private var _emails: List<Email>? = null

    val emails: List<Email>
        get() {
            if (_emails == null) {
                _emails = loadEmails(this)
            }
            return _emails!!
        }
}

/**
 * 应用场景一：惰性初始化
 * 使用委托属性来实现惰性初始化，通过by关键字指定委托对象
 * lazy参数是一个lambda表达式，返回一个线程安全的初始化用于惰性初始化的对象
 */
class People2(override val name: String) : People(name) {
    val emails by lazy { loadEmails(this) }
}

open class PropertyChangeAware {
    protected val changeSupport by lazy { PropertyChangeSupport(this) }
    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

/**
 * 自定义一个可被委托的类
 * 1. 必须按照规定重写getValue和setValue函数
 * 2. 他们都具有有两个参数，第一个表示委托的对象，第二个表示属性对象
 */
class ObservableProperty(
    private var propValue: Int,
    private val changeSupport: PropertyChangeSupport
) {
    operator fun getValue(p: PeopleChangeAware, prop: KProperty<*>): Int = propValue
    operator fun setValue(p: PropertyChangeAware, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

class PeopleChangeAware(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
    /**
     * 使用委托属性委托给自定义的对象，当数据发生变化时发出通知
     */
    var age: Int by ObservableProperty(age, changeSupport)
    var salary: Int by ObservableProperty(salary, changeSupport)
}

/**
 * 应用场景二：监听属性变化，可以在修改属性的时候插入变化的监听器
 *
 */
class PeopleDelegateObservable(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
    private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
    /**
     * 使用委托对象，委托给Delegates.observable 返回的对象
     */
    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)
}

/**
 * 应用场景三：在动态定义属性集的对象中使用
 */
class PeopleMap {
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    /**
     * 手动从map中检索
     */
    val firstName: String
        get() = _attributes["firstName"]!!

    /**
     * 直接委托给`_attributes`处理，因为Map和MutableMap已经实现了setValue和getValue
     */
    val lastName: String by _attributes

}
