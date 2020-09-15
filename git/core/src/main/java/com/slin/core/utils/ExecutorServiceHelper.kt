package com.slin.core.utils

import com.slin.core.config.Constants
import java.util.concurrent.*


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */
object ExecutorServiceHelper {

    private const val THREAD_TAG: String = Constants.GLOBAL_TAG + "_thread" //线程名
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    private val MAX_NUMBER_OF_POOLS = NUMBER_OF_CORES * 2 + 1
    private const val KEEP_ALIVE_TIME: Long = 60L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS


    fun newFixExecutorService(): ExecutorService {
        return ThreadPoolExecutor(
            NUMBER_OF_CORES, MAX_NUMBER_OF_POOLS, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
            LinkedBlockingDeque<Runnable>(), DefaultThreadFactory(THREAD_TAG, false)
        )
    }

    fun newCacheExecutorService(): ExecutorService {
        return ThreadPoolExecutor(
            0, Int.MAX_VALUE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
            SynchronousQueue(), DefaultThreadFactory(THREAD_TAG, false)
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
