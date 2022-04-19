package com.slin.study.kotlin.sort

import org.junit.Test

/**
 * Sort
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/4/19
 */
class SortTest {

    private val data = arrayOf(8, 4, 2, 5, 0, 1, 2, 3, 5)

    /**
     * 冒泡排序
     */
    @Test
    fun bubbleSort() {
        for (i in data.lastIndex downTo 0) {
            for (j in 0 until i) {
                if (data[j] > data[j + 1]) {
                    val tmp = data[j]
                    data[j] = data[j + 1]
                    data[j + 1] = tmp
                }
            }
        }
        println(data.joinToString(", "))
    }

    /**
     * 选择排序
     */
    @Test
    fun selectionSort() {
        for (i in data.indices) {
            var maxIndex = 0
            for (j in 0 until data.size - i) {
                if (data[j] > data[maxIndex]) {
                    maxIndex = j
                }
            }
            val tmp = data[maxIndex]
            data[maxIndex] = data[data.lastIndex - i]
            data[data.lastIndex - i] = tmp
        }
        println(data.joinToString(", "))
    }

    @Test
    fun quicklySort() {
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                if (data[i] > data[j]) {
                    val tmp = data[i]
                    data[i] = data[j]
                    data[j] = tmp
                }
            }
        }
        println(data.joinToString(", "))
    }

}