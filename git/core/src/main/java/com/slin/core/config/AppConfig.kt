package com.slin.core.config

import okhttp3.Interceptor


/**
 * author: slin
 * date: 2020/9/8
 * description:
 *
 */

const val APP_CONFIG_MODULE_TAG = "app_config_module_tag"

data class AppConfig(
        val baseUrl: String = "",
        val timeOutSeconds: Long = 10L,
        val customInterceptors: List<Interceptor>? = null,

        )
