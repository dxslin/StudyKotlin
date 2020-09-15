package com.slin.core.config

import com.slin.core.BuildConfig
import com.slin.core.CoreApplication
import com.slin.core.image.ApplyGlideOptions
import com.slin.core.utils.ExecutorServiceHelper
import com.slin.core.utils.FileUtils
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.ExecutorService


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
    val coreApplication: CoreApplication,
    val baseUrl: String = DefaultConfig.BASE_URL,
    val timeOutSeconds: Long = DefaultConfig.TIME_OUT_SECONDS,
    val customInterceptors: List<Interceptor>? = null,
    val httpLogLevel: HttpLoggingInterceptor.Level = DefaultConfig.HTTP_LOG_LEVEL,
    val cacheFile: File = FileUtils.getCacheFile(coreApplication),
    val glideOptions: ApplyGlideOptions? = null,
    val executorService: ExecutorService = ExecutorServiceHelper.newCacheExecutorService()

)
