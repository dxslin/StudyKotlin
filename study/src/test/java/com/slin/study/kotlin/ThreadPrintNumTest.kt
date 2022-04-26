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

    private val lock = Any()

    @Test
    fun mapThreadTest(){
        val map = hashMapOf<Int, String>()
        val threads = mutableListOf<Thread>()
        (0..10).forEach { i ->
            threads += thread(name = "thread$i") {
                 (0..1000000).forEach { j ->
                     map[1] = "${Thread.currentThread().name} - $i - $j"
//                     Thread.sleep(2)
                 }
             }
        }

       threads += thread {
            repeat(10){ i ->
                repeat(1000){j ->
                    map[1] = "todo"
                    println(map[1])
                }
            }
        }

        threads.forEach { it.join() }
//        map.forEach { (k, v) -> println("$k: $v") }
    }

    @Test
    fun threadTest() {
        println("sync out start")
        val thread = thread {
            synchronized(lock) {
                println("test start")
                Thread.sleep(1000)
                destroy()
                Thread.sleep(3000)
                println("test end")
            }
            println("sync out end")
        }
        thread.join()
    }

    private fun destroy() {
//       val thread = thread {
        println("destroy sync out start")
        synchronized(lock) {
            println("destroy start")
            Thread.sleep(1000)
            println("destroy end")
        }
        println("destroy sync out end")
//        }
//        thread.join()
    }

    fun println(msg: String) {
        System.out.println("${System.currentTimeMillis()}: $msg")
    }

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