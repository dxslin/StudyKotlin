package com.slin.core.net

import android.annotation.SuppressLint
import com.slin.core.config.DefaultConfig
import com.slin.core.utils.ExecutorServiceHelper
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/9/21
 * description: Results结果适配器，将Response转换为我们自定义的Results，加入异常错误处理，适用于kotlin协程挂起函数使用
 * 返回类型为 Results<*>
 *
 */
@SuppressLint("NewApi")
class ResultsCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        check(returnType is ParameterizedType) {
            ("Call return type must be parameterized  as Call<Results<*>>")
        }
        val innerType = getParameterUpperBound(0, returnType)

        return if (getRawType(innerType) == Results::class.java) {
            check(innerType is ParameterizedType) {
                "Results return type must be parameterized as Results<*>"
            }
            val bodyType = getParameterUpperBound(0, innerType)
            ResultsCallAdapter<Any>(bodyType)
        } else {
            null
        }
    }

}

class ResultsCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<Results<R>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): Call<Results<R>> {
        return ResultExecutorCall(ExecutorServiceHelper.IOExecutor, call)
    }


}

internal class ResultExecutorCall<R>(private val executor: ExecutorService, val delegate: Call<R>) :
    Call<Results<R>> {


    override fun clone(): Call<Results<R>> {
        return ResultExecutorCall(executor, delegate)
    }

    override fun execute(): Response<Results<R>> {
        val future = executor.submit<Response<R>> {
            delegate.execute()
        }
        return try {
            val response = future.get(DefaultConfig.TIME_OUT_SECONDS, TimeUnit.SECONDS)
            Response.success(Results.create(response))
        } catch (e: Exception) {
            Response.success(Results.create(e))
        }
    }

    override fun enqueue(callback: Callback<Results<R>>) {
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (delegate.isCanceled) {
                    callback.onResponse(
                        this@ResultExecutorCall,
                        Response.success(Results.create(Errors.IOError(CancelRequestException("Request is canceled"))))
                    )
                } else {
                    callback.onResponse(
                        this@ResultExecutorCall,
                        Response.success(Results.create(response))
                    )
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(this@ResultExecutorCall, Response.success(Results.create(t)))
            }

        })
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

}

