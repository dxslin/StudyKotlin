package com.slin.git

import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig
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


    override val di: DI = subDI(super.di) {
        bind<SlinGitApplication>() with singleton {
            this@SlinGitApplication
        }

        import(configModule)
        import(apiServiceModule)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun createAppConfig(directDIAware: DirectDIAware): AppConfig {
        return directDIAware.run {
            super.createAppConfig(directDIAware).copy(
                baseUrl = Config.BASE_URL,
                customInterceptors = instance()

            )
        }
    }
}
