package com.slin.core.di

import com.slin.core.config.AppConfig
import com.slin.core.config.DefaultConfig
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton


/**
 * author: slin
 * date: 2020/9/9
 * description:
 *
 */
const val CONFIG_MODULE_TAG = "config_module_tag"

val defaultConfigModule = DI.Module(CONFIG_MODULE_TAG) {

    bind<AppConfig>() with singleton {
        AppConfig(
                baseUrl = DefaultConfig.BASE_URL,
                timeOutSeconds = DefaultConfig.TIME_OUT_SECONDS
        )
    }

}
