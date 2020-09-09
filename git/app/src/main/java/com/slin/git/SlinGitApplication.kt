package com.slin.git

import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig
import com.slin.core.net.GitAuthInterceptor
import com.slin.git.config.Config
import okhttp3.Interceptor
import org.kodein.di.*


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
class SlinGitApplication : CoreApplication() {
    override val di: DI = subDI(super.di) {
        bind<SlinGitApplication>() with singleton {
            this@SlinGitApplication
        }

        bind<Interceptor>("GitAuthInterceptor") with provider {
            GitAuthInterceptor()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun createAppConfig(): AppConfig {
        return super.createAppConfig().copy(
                baseUrl = Config.BASE_URL,
                timeOutSeconds = Config.TIME_OUT_SECONDS
        )
    }

}
