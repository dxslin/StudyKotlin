package com.slin.core.config

import okhttp3.Interceptor


/**
 * author: slin
 * date: 2020/9/8
 * description:
 *
 */

const val APP_CONFIG_MODULE_TAG = "app_config_module_tag"

object DefaultConfig {
    const val TIME_OUT_SECONDS = 10L
    const val BASE_URL = "https://api.github.com/"
}


data class AppConfig(
        val baseUrl: String = DefaultConfig.BASE_URL,
        val timeOutSeconds: Long = DefaultConfig.TIME_OUT_SECONDS,
        val customInterceptors: List<Interceptor>? = null,

        )
