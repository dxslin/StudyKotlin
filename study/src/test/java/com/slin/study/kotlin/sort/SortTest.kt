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

    private val data = arrayOf(8, 4, 2, 5, 9, 0, 1, 2, 3, 5, 6)

    /**
     * 冒泡排序
     *
     * 相邻两个数比较，较大的数往后面移动，后面的数据则是排序好的数据
     *
     * 时间复杂度 O(n^2)  空间复杂度 O(1) 稳定排序  原地排序
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
     *
     * 从未排序好的数据里面选择最大/小值放到最后/前面，后/前面的数据则是排序好的
     *
     * 时间复杂度 O(n^2)  空间复杂度 O(1) 非稳定排序  原地排序
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

    /**
     * 插入排序
     *
     * 从未排序好的数据中取出一个数据再已排序好的数据中比较，找到其合适的位置插入
     *
     * 时间复杂度 O(n^2)  空间复杂度 O(1) 稳定排序  原地排序
     */
    @Test
    fun insertSort() {
        for (i in data.indices) {
            for (j in 0 until i) {
                if (data[i] < data[j]) {
                    val tmp = data[i]
                    data[i] = data[j]
                    data[j] = tmp
                }
            }
        }
        println(data.joinToString(", "))
    }

    /**
     * 希尔排序
     *
     * 希尔排序的思想是采用插入排序的方法，先让数组中任意间隔为 h 的元素有序，刚开始 h 的大小可以是 h = n / 2,接着
     * 让 h = n / 4，让 h 一直缩小，当 h = 1 时，也就是此时数组中任意间隔为1的元素有序，此时的数组就是有序的了。
     *
     * 时间复杂度：O(nlogn)  空间复杂度：O(1)  非稳定排序  原地排序
     */
    @Test
    fun shellSort() {
        var h = data.size / 2
        while (h > 0) {
            for (i in h until data.size) {
                val tmp = data[i]
                for (j in i - h downTo 0 step h) {
                    if (data[j] > tmp) {
                        data[j + h] = data[j]
                        data[j] = tmp
                    } else {
                        break
                    }
                }
            }
            h /= 2
        }
        println(data.joinToString(", "))
    }

    @Test
    fun quicklySortTest() {
        quicklySort(data, 0, data.lastIndex)
        println(data.joinToString(", "))
    }

    /**
     * 快速排序
     *
     * 选择一个元素作为中轴，然后将小于该元素的放在左边，大于等于该元素的放在右边，这样中轴处是有序的；
     * 然后在将中轴处分为两个数组，使用递归重复操作
     *
     * 时间复杂度：O(nlogn) 空间复杂度：O(logn)  非稳定排序 原地排序
     */
    private fun quicklySort(data: Array<Int>, left: Int, right: Int) {
        var low = left
        var high = right
        if (low >= high) {
            return
        }
        val pivot = data[low]
        while (low < high) {
            while (low < high && data[high] >= pivot) {
                high--
            }
            data[low] = data[high]
            while (low < high && data[low] <= pivot) {
                low++
            }
            data[high] = data[low]
        }
        data[low] = pivot
        quicklySort(data, left, low - 1)
        quicklySort(data, low + 1, right)
    }


    /**
     * 归并排序
     *
     * 将一个大的无序数组有序，我们可以把大的数组分成两个，然后对这两个数组分别进行排序，之后在把这两个数组合并成一个
     * 有序的数组。由于两个小的数组都是有序的，所以在合并的时候是很快的。
     *
     * 时间复杂度：O(nlogn)  空间复杂度：O(n)  稳定排序  非原地排序
     */
    @Test
    fun mergeSort() {
        var i = 1
        while (i < data.size) {
            var left = 0
            var mid = left + i - 1
            var right = mid + i
            while (right < data.size) {
                merge(data, left, mid, right)
                left = right + 1
                mid = left + i - 1
                right = mid + i
            }
            if (left < data.size && mid < data.size) {
                merge(data, left, mid, data.size - 1)
            }
            i += i
        }
        println(data.joinToString(", "))
    }

    private fun merge(data: Array<Int>, left: Int, mid: Int, right: Int) {
        val arr = Array(right - left + 1) { 0 }
        var i = left
        var j = mid + 1
        var k = 0
        while (i <= mid && j <= right) {
            if (data[i] < data[j]) {
                arr[k++] = data[i++]
            } else {
                arr[k++] = data[j++]
            }
        }
        while (i <= mid) {
            arr[k++] = data[i++]
        }
        while (j <= right) {
            arr[k++] = data[j++]
        }
        i = left
        for (a in arr.indices) {
            data[i++] = arr[a]
        }
    }

    /**
     * 堆排序
     *
     * 堆的特点是堆顶的元素是一个最值，大顶堆的堆顶是最大值，小顶堆的堆顶是最小值
     *
     * 堆排序就是将堆顶和最后一个元素交换，这样破坏了堆的特性，然后再将堆中剩余的元素构成一个大顶堆，再把堆顶与倒数第二个
     * 元素交换，如此往复操作，直到剩余的堆只剩下一个元素，这时数组就是有序的了
     *
     * 时间复杂度：O(nlogn)  空间复杂度：O(1)  非稳定排序  原地排序
     *
     * 堆排序较之快速排序和归并排序的好处是最坏情况也是O(nlogn)
     */
    @Test
    fun headSort() {
        val size = data.size
        for (i in (size - 2) / 2 downTo 0) {
            downAdjust(data, i, size - 1)
        }
        for (i in size - 1 downTo 1) {
            val tmp = data[i]
            data[i] = data[0]
            data[0] = tmp
            downAdjust(data, 0, i - 1)
        }
        println(data.joinToString(", "))
    }

    private fun downAdjust(arr: Array<Int>, head: Int, last: Int) {
        // 父节点位置
        var parent = head
        // 左侧子节点位置
        var child = 2 * head + 1
        // 暂存父节点值，后面将其下沉
        val tmp = arr[parent]
        while (child <= last) {
            // 比较左右节点，取较大的一个
            if (child + 1 <= last && arr[child] < arr[child + 1]) {
                child++
            }
            // 如果父节点大于等于子节点，则不需要下沉
            if (arr[child] <= tmp)
                break
            else {
                // 下沉
                arr[parent] = arr[child]
                parent = child
                child = 2 * parent + 1
            }
        }
        arr[parent] = tmp
    }

    private fun down(arr: Array<Int>, head: Int, last: Int) {
        var parent = head
        var child = 2 * parent + 1
        val tmp = arr[child]
        while (child <= last) {
            if(child + 1 < last && arr[child] < arr[child + 1]){
                child++
            }
            if(arr[child] <= tmp){
                break
            } else {
                arr[parent] = arr[child]
                parent = child
                child = 2 * parent + 1
            }
        }
        arr[parent] = tmp
    }

}