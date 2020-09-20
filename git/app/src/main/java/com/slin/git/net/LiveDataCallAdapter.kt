package com.slin.git.net

import androidx.lifecycle.LiveData
import com.slin.core.net.Results
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * author: slin
 * date: 2020-09-20
 * description: 转换器，将Response转换为 LiveData<Results<R>>
 */

/**
 * the LiveDataCallAdapter factory
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        //如果api接口函数使用了kotlin的挂起函数suspend，那么这里类型应该是Call<LiveData<*>>，不会与之匹配，请不要使用
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != Results::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}

/**
 * 转换器
 *
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<Results<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): LiveData<Results<R>> {
        return object : LiveData<Results<R>>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(Results.create(response))
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(Results.create(t))
                        }

                    })
                }
            }
        }
    }
}
