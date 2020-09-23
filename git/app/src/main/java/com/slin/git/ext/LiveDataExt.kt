package com.slin.git.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.slin.core.net.Results
import com.slin.core.net.WaitTimeOutException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

/**
 * author: slin
 * date: 2020-09-20
 * description:
 */
//fun <T> LiveData<T>.getOrAwaitValue(
//    time: Long = 200,
//    timeUnit: TimeUnit = TimeUnit.SECONDS,
//    afterObserve: () -> Unit = {}
//): T {
//    var data: T? = null
//    val latch = CountDownLatch(1)
//    val observer = object : Observer<T> {
//        override fun onChanged(o: T?) {
//            data = o
//            latch.countDown()
//            this@getOrAwaitValue.removeObserver(this)
//        }
//    }
//    this.observeForever(observer)
//
//    afterObserve.invoke()
//
//    // Don't wait indefinitely if the LiveData is not set.
//    if (!latch.await(time, timeUnit)) {
//        this.removeObserver(observer)
//        throw TimeoutException("LiveData value was never set.")
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    return data as T
//}

suspend fun <T> LiveData<Results<T>>.getOrAwaitValue(
    time: Long = 200,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): Results<T> {
    val channel = Channel<Results<T>>()
    val observer = object : Observer<Results<T>> {
        override fun onChanged(o: Results<T>) {
            channel.offer(o)
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    observeForever(observer)

    afterObserve.invoke()

    return withTimeout(timeUnit.toMillis(time)) {
        try {
            channel.receive()
        } catch (e: TimeoutCancellationException) {
            Results.create(WaitTimeOutException("LiveData value was never set in $time ${timeUnit.name}"))
        }
    }
}
