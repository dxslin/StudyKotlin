package com.slin.core.net

import android.annotation.SuppressLint
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture


/**
 * author: slin
 * date: 2020/9/21
 * description: Results结果适配器，将Response转换为我们自定义的Results，加入异常错误处理
 * 这种适合java使用，不适用于kotlin协程
 * 返回类型为 CompletableFuture<Results<*>>
 *
 */
@SuppressLint("NewApi")
class RxResultsCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != CompletableFuture::class.java) {
            return null
        }
        check(returnType is ParameterizedType) {
            ("CompletableFuture return type must be parameterized  as CompletableFuture<Results<*>>")
        }
        val innerType = getParameterUpperBound(0, returnType)

        return if (getRawType(innerType) == Results::class.java) {
            check(innerType is ParameterizedType) {
                "Results return type must be parameterized as Results<*>"
            }
            val bodyType = getParameterUpperBound(0, innerType)
            RxResultsCallAdapter<Any>(bodyType)

        } else {
            null
        }
    }

}

@SuppressLint("NewApi")
internal class RxResultsCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, CompletableFuture<Results<R>>> {
    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): CompletableFuture<Results<R>> {
        val future: CallCancelCompletableFuture<Results<R>> = CallCancelCompletableFuture(call)
        call.enqueue(ResultsCallback(future))
        return future

    }


    inner class ResultsCallback(private val future: CompletableFuture<Results<R>>) : Callback<R> {

        override fun onFailure(call: Call<R>, t: Throwable) {
            future.complete(Results.create(t))
        }

        override fun onResponse(call: Call<R>, response: Response<R>) {
            future.complete(Results.create(response))
        }
    }
}


@SuppressLint("NewApi")
internal class CallCancelCompletableFuture<T>(private val call: Call<*>) : CompletableFuture<T>() {
    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        if (mayInterruptIfRunning) {
            call.cancel()
        }
        return super.cancel(mayInterruptIfRunning)
    }
}