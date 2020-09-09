package com.slin.git

import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig
import com.slin.core.config.DefaultConfig
import com.slin.core.di.DEFAULT_SHARE_PREFERENCES_TAG
import com.slin.git.net.GitAuthInterceptor
import com.slin.git.stroage.local.GitUserInfoStorage
import okhttp3.Interceptor
import org.kodein.di.*


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
class SlinGitApplication : CoreApplication() {

//    override val appConfig: AppConfig = super.appConfig.copy(
//                baseUrl = Config.BASE_URL,
//                timeOutSeconds = Config.TIME_OUT_SECONDS,
//                customInterceptors = Config.INTERCEPTORS.flatMap {
//                    super.appConfig.customInterceptors ?: listOf()
//                },
//        )

    override val configModule: DI.Module = super.configModule.copy {
        this.containerBuilder.subBuilder()

        bind<AppConfig>() with singleton {
            AppConfig(
                    baseUrl = DefaultConfig.BASE_URL,
                    timeOutSeconds = DefaultConfig.TIME_OUT_SECONDS
            )
        }

        bind<List<Interceptor>>() with singleton {
            listOf<Interceptor>(
                    GitAuthInterceptor(instance())
            )
        }

        bind<GitUserInfoStorage>() with singleton {
            GitUserInfoStorage.getInstance(instance(DEFAULT_SHARE_PREFERENCES_TAG))
        }

    }

    override val di: DI = subDI(super.di) {
        bind<SlinGitApplication>() with singleton {
            this@SlinGitApplication
        }

    }

    override fun onCreate() {
        super.onCreate()
    }

}
