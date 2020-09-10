package com.slin.core.config

import com.slin.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor


/**
 * author: slin
 * date: 2020/9/8
 * description: app配置
 *
 */


object DefaultConfig {
    const val TIME_OUT_SECONDS = 10L
    const val BASE_URL = "https://api.github.com/"
    val HTTP_LOG_LEVEL = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        false -> HttpLoggingInterceptor.Level.NONE
    }
}


data class AppConfig(
    val baseUrl: String = DefaultConfig.BASE_URL,
    val timeOutSeconds: Long = DefaultConfig.TIME_OUT_SECONDS,
    val customInterceptors: List<Interceptor>? = null,
    val httpLogLevel: HttpLoggingInterceptor.Level = DefaultConfig.HTTP_LOG_LEVEL

)
