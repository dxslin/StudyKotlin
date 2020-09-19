package com.slin.core.net

import retrofit2.Response
import java.io.IOException

/**
 * author: slin
 * date: 2020-09-07
 * description: 网络接口访问，返回状态
 */
fun <T> processApiResponse(response: Response<T>): Results<T> {
    return try {
        val code = response.code()
        val msg = response.message()
        if (response.isSuccessful) {
            Results.Success(response.body()!!)
        } else {
            Results.Failure(Errors.DataError(code, msg))
        }
    } catch (e: IOException) {
        Results.Failure(Errors.IOError(e))
    }
}

