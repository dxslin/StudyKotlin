package com.slin.study.kotlin.samples.type

import com.slin.study.kotlin.support.CollectionUtils
import java.io.BufferedReader
import java.io.File
import java.io.StringReader


/**
 * author: slin
 * date: 2020-02-11
 * description:集合与数组
 * 1. 注意区分可包含null的集合 与 可为null的集合
 * 2. 只读集合和可变集合,kotlin将集合的读操作和写操作区分开来，形成了只读集合和可变集合(mutable开头)，
 *      可变集合继承与只读集合，只读集合不一定不可修改，其内部实现可能是能够修改的，而且不一定是线程安全的
 * 3. 集合创建函数
 *      ***********************************集合创建函数************************************
 *      集合类型            只读                          可变
 *      List                listOf              mutableListOf、arrayListOf
 *      Set                 setOf               mutableSetOf、hashSetOf、linkedSetOf、sortedSetOf
 *      Map                 mapOf               mutableMapOf、hashMapOf、linkedMapOf、sortedMapOf
 * 4. kotlin是无法拒绝Java修改只读集合的
 * 5. kotlin在实现Java接口时，参数需要考虑 集合的是否包含null、集合是否可以为null以及集合是否可修改
 * 6. 数组，数组的工厂函数，基本数据类型数组，数组的遍历
 */
fun main() {
    //创建可以包含null的集合
    val canNullList = ArrayList<Int?>()
    canNullList.addAll(arrayListOf(1, null, 3, 5))
    println("包含空值的集合：$canNullList")
    //创建一个可能为空的集合
    var maybeNullList: ArrayList<Int>? = null
    println("可以为空的集合：$maybeNullList")
    maybeNullList = arrayListOf(1, 3, 5)
    println("可以为空的集合：$maybeNullList")

    val numbersList = readNumbers(BufferedReader(StringReader("12\nabv\n24")))
    addValidNumbers(numbersList)
    //addValidNumbers的另一种写法
    val validNumbers = numbersList.filterNotNull()
    println("Sum of valid numbers: ${validNumbers.sum()}")
    println("Invalid numbers: ${numbersList.size - validNumbers.size}")

    val copyList = ArrayList<Int?>()
    copyElements(numbersList, copyList)
    println("copy elements: $copyList")

    printInUppercase(arrayListOf("slin", "kotlin", "java"))

    //数组 arrayOf创建数组，基本类型数据是装箱了的
    val numArray = arrayOf(1, 2, 3, 4, null)
    //数组不能直接打印，这里会打印一个地址
    println(numArray)
    numArray.printWithPrefix("num array: ")

    //创建一个给定大小的数组
    val sizeArray = arrayOfNulls<String>(3)
    sizeArray.printWithPrefix("null size array: ")
    sizeArray[0] = "1"
    sizeArray[1] = null
    sizeArray[2] = "3"
    sizeArray.printWithPrefix("value size array: ")

    val letters = Array(26) { i -> ('a' + i).toString() }
    println("letters: ${letters.contentToString()}")

    val strings = listOf('a', 'b', 'c')
    //向vararg方法传递集合，使用展开运算符“*”传递数组
    println("%s%s%s".format(*strings.toTypedArray()))

    //基本数据类型+ArrayOf（如intArrayOf、charArrayOf） 创建数组，基本数据类型是没有装箱的
    val intArray = IntArray(5) { i -> i * i }
    val intArray2 = intArrayOf(1, 2, 3, 4, 5)
    val charArray = charArrayOf('a', 'b', 'c')
    println("intArray: ${intArray.joinToString("")}")
    println("intArray2: ${intArray2.contentToString()}")
    println("charArray: ${charArray.contentToString()}")

}

/**
 * 将文本读取成数字存入list中
 */
fun readNumbers(reader: BufferedReader): List<Int?> {
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        try {
            val num = line.toInt()
            result.add(num)
        } catch (e: NumberFormatException) {
            //如果不能转换为数字添加null
            result.add(null)
        }
    }
    return result
}

/**
 * 计算有效数字的和
 */
fun addValidNumbers(numbers: List<Int?>): Int {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (num in numbers) {
        if (num != null) {
            sumOfValidNumbers += num
        } else {
            invalidNumbers++
        }
    }
    println("Sum of valid numbers: $sumOfValidNumbers")
    println("Invalid numbers: $invalidNumbers")
    return sumOfValidNumbers
}

/**
 * 复制集合，可变集合与只读集合的操作示例
 */
fun <T> copyElements(source: Collection<T>, target: MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

/**
 * 调用Java的静态方法
 * 1. kotlin是无法拒绝Java修改只读集合的
 */
fun printInUppercase(list: List<String>) {
    println(CollectionUtils.uppercaseAll(list))
    println(list.first())
}

/**
 * kotlin在实现Java接口时，参数需要考虑 集合的是否包含null、集合是否可以为null以及集合是否可修改
 */
class FileIndex : CollectionUtils.FileContentProcessor, CollectionUtils.DataParser<Person> {
    override fun processContents(
        path: File,
        binaryContents: ByteArray?,
        textContents: List<String>?
    ) {
        //do something
    }

    override fun parseData(
        input: String,
        output: MutableList<Person>,
        errors: MutableList<String?>
    ) {
        //do something
    }

}

/**
 * 对数组使用forEachIndexed
 */
fun <T> Array<T>.printWithPrefix(prefix: String = "") {
    this.forEachIndexed { index, element ->
        run {
            when {
                index == 0 -> {
                    print("$prefix [$element ")
                }
                index < this.size - 1 -> {
                    print("$element ")
                }
                else -> {
                    print("$element]\r\n")
                }
            }
        }
    }
}
