package com.slin.study.kotlin.samples.`class`

/**
 * author: slin
 * date: 2020-01-27
 * description:数据类和类委托
 */

fun main() {
    val client10 = Client1("slin", 12)
    val client11 = Client1("slin", 12)
    println("client10: $client10")
    println("client11: $client11")
    println("client10 == client11: ${client10 == client11}")

    val client20 = Client2("slin", 12)
    val client21 = Client2("slin", 12)
    println("client20: $client20")
    println("client21: $client21")
    println("client20 == client21: ${client20 == client21}")

    val client22 = client20.copy(postalCode = 20)
    println("client22: $client22")


    val countingSet = CountingSet<Int>()
    println("before add: countingSet ${countingSet.objectCount}")
    countingSet.addAll(setOf(1, 4, 3))
    println("after add: countingSet ${countingSet.objectCount}")
}

/******************************* start: 数据类 **************************************/

class Client1(val name: String, val postalCode: Int) {

    /**
     * 重写equal方法
     */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client1) {
            return false
        }
        return name == other.name && postalCode == other.postalCode
    }

    /**
     * 重写toString方法
     */
    override fun toString(): String = "Client1(name:$name, postalCode:$postalCode)"

    /**
     * 重写hashCode方法
     */
    override fun hashCode(): Int = name.hashCode() * 31 + postalCode

    /**
     * 数据类会自动生成copy方法，实现方式如下
     * 因为数据类的属性建议做成val的，不方便修改，因此kotlin创建了一个copy方法来实现，
     *      copy出来的对象拥有独立的生命周期，不会影响到原有的数据
     */
    fun copy(name: String = this.name, postalCode: Int = this.postalCode) =
        Client1(name, postalCode)

}

/**
 * 数据类定义
 * 1. 通过data关键字标注为数据类
 * 2. 数据类会自动重写equals/hashCode/toString方法
 * 3. 数据类会生成一个copy方法
 */
data class Client2(val name: String, val postalCode: Int)


/******************************* end: 数据类 **************************************/

/******************************* start: 类委托 by **************************************/
/**
 * 正常实现一个接口
 */
class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()

    override val size: Int = innerList.size

    override fun contains(element: T): Boolean = innerList.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)

    override fun isEmpty(): Boolean = innerList.isEmpty()

    override fun iterator(): Iterator<T> = innerList.iterator()

}

/**
 * 类委托
 * 1. 在类定义后面使用“by 成员变量”，将需要实现的方法委托给该成员变量的类型去实现
 */
class DelegatingCollection2<T>(private val innerList: List<T> = ArrayList()) :
    Collection<T> by innerList

class CountingSet<T>(private val innerSet: MutableCollection<T> = HashSet()) :
    MutableCollection<T> by innerSet {

    var objectCount: Int = 0

    /**
     * 重写add方法，实现计数objectCount
     */
    override fun add(element: T): Boolean =
        if (innerSet.add(element)) {
            objectCount += 1
            true
        } else {
            false
        }

    /**
     * 重写add方法，实现计数objectCount
     */
    override fun addAll(elements: Collection<T>): Boolean =
        if (innerSet.addAll(elements)) {
            objectCount += elements.size
            true
        } else {
            false
        }

    /**
     * 重写add方法，实现计数objectCount
     */
    override fun clear() {
        innerSet.clear()
        objectCount = 0
    }
}


/******************************* end: 数据类 **************************************/


