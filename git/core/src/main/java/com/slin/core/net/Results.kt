package com.slin.core.net

import retrofit2.Response

/**
 * author: slin
 * date: 2020-09-07
 * 网络请求结果状态封装，成功或失败
 * @param <T>
 */
sealed class Results<out T> {

    companion object {

        fun <T> create(t: Throwable): Results<T> {
            return Failure(t)
        }

        fun <T> create(response: Response<T>): Results<T> {
            val code = response.code()
            val msg = response.message()
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || code == 204) {
                    Failure(Errors.DataError(Errors.EMPTY_RESULT_CODE))
                } else {
                    Success(body)
                }
            } else {
                Failure(Errors.DataError(code, msg))
            }
        }
    }

    data class Success<out T : Any>(val data: T) : Results<T>()
    data class Failure(val throwable: Throwable) : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[throwable=$throwable]"
        }
    }
}