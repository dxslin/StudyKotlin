package com.slin.core.net

/**
 * author: slin
 * date: 2020-09-07
 * 网络请求结果状态封装，成功或失败
 * @param <T>
 */
sealed class Results<out T> {

    data class Success<out T : Any>(val data: T) : Results<T>()
    data class Failure(val throwable: Throwable) : Results<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[throwable=$throwable]"
        }
    }
}