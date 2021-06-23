package com.slin.study.kotlin.executor

import org.junit.Test
import java.util.*
import java.util.concurrent.*

/**
 * author: slin
 * date: 2021/6/15
 * description:
 *
 */
class ExecutorTest {

    @Test
    fun executor() {
        var num = 0
//        val t = thread {

        val factory = ThreadFactory { r ->
            val th = Thread(r, "slin_thread_${num++}")
            println("${Date()} slin : create ${th.name}")
            th
        }
        val blockingQueue: BlockingQueue<Runnable> = SynchronousQueue()
//            val executor = Executors.newFixedThreadPool(2, factory)
//            val executor = Executors.newCachedThreadPool(factory)
//            val executor = ThreadPoolExecutor(2, 10, 10,
//                    TimeUnit.SECONDS, LinkedBlockingQueue(), factory, ThreadPoolExecutor.DiscardPolicy())
        val executor = ThreadPoolExecutor(
            2, 10, 10,
            TimeUnit.SECONDS, blockingQueue, factory, ThreadPoolExecutor.DiscardPolicy()
        )


        for (i in 0..20) {
            println("${Date()} slin ${Thread.currentThread().name}: submit $i")
            executor.submit {
                println("${Date()} slin ${Thread.currentThread().name}: execute $i")
                timeConsumeTask()
                println("${Date()} slin ${Thread.currentThread().name}: end $i")
            }
        }

//        }

//        t.join()

        Thread.sleep(100_000)

    }

    fun timeConsumeTask() {
        var p = 0
        val max = 100000
        for (i in 0..max) {
            for (j in 0..max) {
                for (k in 0..max) {
                    p++
                }
            }
        }
    }

}