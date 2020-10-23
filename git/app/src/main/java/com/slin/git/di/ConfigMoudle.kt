package com.slin.git.di

import com.slin.core.config.ApplyRetrofitOptions
import com.slin.core.di.DEFAULT_SHARE_PREFERENCES_TAG
import com.slin.git.api.local.GitUserInfoStorage
import com.slin.git.net.GitAuthInterceptor
import com.slin.git.net.LiveDataCallAdapterFactory
import okhttp3.Interceptor
import org.kodein.di.*
import retrofit2.Retrofit


/**
 * author: slin
 * date: 2020/9/10
 * description: app动态配置项，需要依赖注入的配置在这里
 *
 */
const val CONFIG_MODULE_TAG = "config_module_tag"

val configModule = DI.Module(CONFIG_MODULE_TAG) {
    bind<List<Interceptor>>() with provider {
        listOf<Interceptor>(
            GitAuthInterceptor(instance())
        )
    }

    bind<GitUserInfoStorage>() with singleton {
        GitUserInfoStorage.getInstance(instance(DEFAULT_SHARE_PREFERENCES_TAG))
    }

    //config retrofit return LiveData<Results>
    bind<ApplyRetrofitOptions>() with provider {
        object : ApplyRetrofitOptions {
            override fun apply(builder: Retrofit.Builder) {
                builder.addCallAdapterFactory(instance<LiveDataCallAdapterFactory>())
            }
        }
    }

    bind<LiveDataCallAdapterFactory>() with provider {
        LiveDataCallAdapterFactory()
    }

}
