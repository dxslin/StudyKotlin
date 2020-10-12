package com.slin.git

import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig
import com.slin.core.config.DefaultConfig
import com.slin.git.config.Config
import com.slin.git.config.configModule
import com.slin.git.di.apiServiceModule
import org.kodein.di.*


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
class SlinGitApplication : CoreApplication() {

    companion object {
        lateinit var INSTANCE: CoreApplication
    }

    override val di: DI = subDI(super.di) {
        bind<SlinGitApplication>() with singleton {
            this@SlinGitApplication
        }

        import(configModule)
        import(apiServiceModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    override fun createAppConfig(directDIAware: DirectDIAware): AppConfig {
        return directDIAware.run {
            super.createAppConfig(directDIAware).copy(
                baseUrl = Config.BASE_URL,
                httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
                customInterceptors = instance(),
                applyRetrofitOptions = instance()
            )
        }
    }
}
