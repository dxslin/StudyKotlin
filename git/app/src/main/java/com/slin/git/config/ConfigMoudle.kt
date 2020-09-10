package com.slin.git.config

import com.slin.core.di.DEFAULT_SHARE_PREFERENCES_TAG
import com.slin.git.net.GitAuthInterceptor
import com.slin.git.stroage.local.GitUserInfoStorage
import okhttp3.Interceptor
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton


/**
 * author: slin
 * date: 2020/9/10
 * description: app动态配置项，需要依赖注入的配置在这里
 *
 */
const val CONFIG_MODULE_TAG = "config_module_tag"

val configModule = DI.Module(CONFIG_MODULE_TAG) {
    bind<List<Interceptor>>() with singleton {
        listOf<Interceptor>(
            GitAuthInterceptor(instance())
        )
    }

    bind<GitUserInfoStorage>() with singleton {
        GitUserInfoStorage.getInstance(instance(DEFAULT_SHARE_PREFERENCES_TAG))
    }

}
