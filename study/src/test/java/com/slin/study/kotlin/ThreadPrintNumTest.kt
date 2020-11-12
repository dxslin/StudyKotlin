package com.slin.study.kotlin

import org.junit.Test
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread


/**
 * author: slin
 * date: 2020/11/9
 * description:
 *
 */
class ThreadPrintNumTest {

    @Test
    fun printNumTest() {
        val lock = ReentrantLock()
        var num = 0
        val oddThread = thread(name = "odd-thread", start = false) {
            while (num < 10) {
                lock.lock()
                if (num % 2 == 1) {
                    println(Thread.currentThread().name + ": " + (num++))
                }
                lock.unlock()
            }
        }
        val evenThread = thread(name = "even-thread", start = false) {
            while (num < 10) {
                lock.lock()
                if (num % 2 == 0) {
                    println(Thread.currentThread().name + ": " + (num++))
                }
                lock.unlock()
            }
        }
        oddThread.start()
        evenThread.start()

        evenThread.join()
        oddThread.join()
    }


}

fun main() {
    ThreadPrintNumTest().printNumTest()
}