package com.slin.core.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */
object ExecutorServiceHelper {

    private const val THREAD_TAG: String = "thread" //线程名
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    private val MAX_NUMBER_OF_POOLS = NUMBER_OF_CORES * 2 + 1
    private const val KEEP_ALIVE_TIME: Long = 60L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    val IOExecutor = newCacheExecutorService()

    fun newFixExecutorService(): ExecutorService {
        return Executors.newFixedThreadPool(
            NUMBER_OF_CORES,
            DefaultThreadFactory(THREAD_TAG, false)
        )
    }

    fun newCacheExecutorService(): ExecutorService {
        return Executors.newCachedThreadPool(
            DefaultThreadFactory(THREAD_TAG, false)
        )
    }

    /**
     * 默认的线程工厂
     */
    private class DefaultThreadFactory internal constructor(
        private val name: String,
        private val daemon: Boolean
    ) :
        ThreadFactory {
        private var threadNum = 0
        override fun newThread(r: Runnable): Thread {
            val thread = Thread(r, name + "_" + threadNum)
            thread.priority = Thread.NORM_PRIORITY
            thread.isDaemon = daemon
            threadNum++
            return thread
        }
    }
}
