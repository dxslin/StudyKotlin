package com.slin.study.kotlin.samples.generics

import kotlin.random.Random


/**
 * author: slin
 * date: 2020/3/11
 * description: 协变：泛型和子类型化
 * 1. 协变是指（以`Producer<T>`为例）如果`A`是`B`的子类型，那么`Producer<A>`是就是`Producer<B>`的子类型，就是说子类型被保留了
 * 2. 协变使用out修饰声明泛型类型，格式：“<out T : 类型>”
 * 3. 协变泛型类型只能作为输出类型，不能作为输入类型，或者说只能用于返回值，不能作为参数输入类型，
 *      如果想要作为参数使用，可以使用注解`@UnsafeVariance`修饰泛型类型
 * 4. 构造函数中如果是公共的`val`属性的可以使用协变泛型类型，因为其只有getter，不包含setter；如果是`var`属性则不能使用
 * 5. 逆变是指（以`Consumer<T>`为例）如果`A`是`B`的子类型，那么`Consumer<B>`是`Consumer<A>`的子类型，即子类型反转了
 * 6. 逆变使用`in`修饰声明泛型类型，格式：“<in T : 类型>”
 * 7. 协变泛型类型只能作为输入类型，不能作为输出类型，或者说只能用于输入参数类型，不能作为返回值类型
 * 8. 构造函数中不能包含公共的`val`或者'var'的属性，因为它们具有`getter`方法
 *
 */
fun main() {
    //协变示例
    val stringList: List<String> = listOf("zqpr", "ad")
    //这里是可以赋值的，因为List接口是支持协变的，`interface List<out E>`，即List<String>被当做List<Any>的子类型
    val anyList: List<Any> = stringList

    val strings: MutableList<String> = mutableListOf("abc", "defg")
    //这里是无法赋值的，因为MutableList是不变的
//    val anys:MutableList<Any> = strings

    val anyComparator: Comparator<Any> = Comparator<Any> { e1, e2 ->
        e1.hashCode() - e2.hashCode()
    }
    //逆变示例，这里sortWith期待的是一个Comparator<String>类型，但是这里传入一个Comparator<Any>类型是可以的
    println(stringList.sortedWith(anyComparator))

    val animal1 = Animal("animal_1")
    val animal2 = Animal("animal_2")
    val cat1 = Cat("cat_1")
    val cat2 = Cat("cat_2")

    val animalList: MutableList<Animal> = mutableListOf(animal1, cat1)
    val catList: MutableList<Cat> = mutableListOf(cat1, cat2)

    println("\nfeed all animals: ")
    val animalHerd = Herd<Animal>(animalList, animal1)
    feedAll(animalHerd)
    println("\ntake care of cats:　")
    val catHerd = Herd<Cat>(catList, cat1)
    takeCareOfCats(catHerd)

    println("animalHerd.equalFirstvalue(animal1): ${animalHerd.equalFirstvalue(animal1)} ")
    println("animalHerd.equalFirstvalue(animal2): ${animalHerd.equalFirstvalue(animal2)} ")

    println(stringList.sortedWith(Comparator { e1, e2 ->
        e1.hashCode() - e2.hashCode()
    }))

    val zoo = Zoo<Animal>("zoo1", animal1)
    zoo.putIn(animal2)
    zoo.putIn(cat1)

    testCopyData<String>("copyDataT: ") { stringList, anyList ->
        copyDataT(stringList, anyList)
    }
    testCopyData<Any>("copyDataTR: ") { stringList, anyList ->
        copyDataTR(stringList, anyList)
    }
    testCopyData<Any>("copyDataOutT: ") { stringList, anyList ->
        copyDataOutT(stringList, anyList)
    }
    testCopyData<Any>("copyDataInT: ") { stringList, anyList ->
        copyDataInT(stringList, anyList)
    }

    val anyNullList: MutableList<Any?> = mutableListOf(1, "22", 3, "444", null)
    val numbers: MutableList<Number> = mutableListOf(1, 2, 3, 4)
    val unkownElementList: MutableList<*> = if (Random.nextBoolean()) anyNullList else numbers
    //使用*代替参数类型投影成了<out Any?>，是无法调用输入类型有参数类型的函数
//    unkownElementList.add(1)
    println(unkownElementList.first())
}


open class Animal(val name: String) {
    fun feed() {
        println("feed $name")
    }
}

class Cat(name: String) : Animal(name) {
    fun cleanLitter() {
        println("clean litter $name")
    }
}

/**
 * 协变
 * 1. 使用`out`修饰声明泛型类型，格式：“<out T : 类型>”
 * 2. 协变泛型类型只能作为输出类型，不能作为输入类型，或者说只能用于返回值，不能作为参数输入类型，
 *      如果想要作为参数使用，可以使用注解`@UnsafeVariance`修饰泛型类型
 * 3. 构造函数中如果是`val`属性的可以使用协变泛型类型，因为其只有`getter`，不包含`setter`；如果是`var`属性则不能使用
 */
class Herd<out T : Animal>(val animals: List<T>, val firstValue: T) {

    val size: Int
        get() = animals.size

    operator fun get(index: Int) = animals[index]

    /**
     * 想要允许使用参数输入，可以使用`@UnsafeVariance`注解修饰
     */
    fun equalFirstvalue(value: @UnsafeVariance T): Boolean {
        return firstValue == value
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
    }
    feedAll(cats)
}

/**
 * 逆变
 * 1. 逆变使用`in`修饰声明泛型类型，格式：“<in T : 类型>”
 * 2. 协变泛型类型只能作为输入类型，不能作为输出类型，或者说只能用于输入参数类型，不能作为返回值类型
 * 3. 构造函数中不能包含公共的`val`或者'var'的属性，因为它们具有`getter`方法
 *
 */
class Zoo<in T : Animal>(val name: String, private val oldAnimal: Animal) {

    private val animals = mutableListOf<T>()

    fun putIn(animal: T) {
        animals.add(animal)
        println("put the ${animal.name} in the $name")
    }

}

/**
 * 测试下面几种点变型函数的方法
 *
 */
fun <R> testCopyData(prefix: String, copyData: (MutableList<String>, MutableList<R>) -> Unit) {
    val stringList: MutableList<String> = mutableListOf("abc", "def", "opq", "ghijk", "rst")
    val anyList: MutableList<R> = mutableListOf()
    copyData(stringList, anyList)
    println(prefix + anyList)
}

/**
 * 带不变类型参数的数据拷贝函数，source和destination必须是相同的类型，不支持变型
 */
fun <T> copyDataT(source: MutableList<T>, destination: MutableList<T>) {
    for (element in source) {
        destination.add(element)
    }
}

/**
 * 带不变类型参数的数据拷贝函数，这里T必须是R的子类型
 */
fun <T : R, R> copyDataTR(source: MutableList<T>, destination: MutableList<R>) {
    for (element in source) {
        destination.add(element)
    }
}

/**
 * 带out投影类型参数的数据拷贝函数
 * 1. 这里`<out T>`等同于Java的通配符`<? extends T>`
 * 2. 方法中只能调用source的获取数据的方法，或者说是输出类型为T的方法，不能调用输入类型是T的方法
 */
fun <T> copyDataOutT(source: MutableList<out T>, destination: MutableList<T>) {
    for (element in source) {
        destination.add(element)
    }
    //调用add方法将报错
//    source.add(source[0])
}

/**
 * 带in投影类型的数据拷贝函数
 * 1. 这里的`<in T>`等同于Java的通配符`<? super T>`
 */
fun <T> copyDataInT(source: MutableList<T>, destination: MutableList<in T>) {
    for (element in source) {
        destination.add(element)
    }
}

/**
 * 星号（*）投影用于无引用参数类型或者对参数类型不感兴趣的地方
 */
fun printFirst(list: List<*>) {
    if (list.isNotEmpty()) {
        println(list.first())
    }
}

