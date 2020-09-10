package com.slin.core

import android.app.Application
import com.slin.core.config.AppConfig
import com.slin.core.di.httpClientModule
import com.slin.core.di.repositoryModule
import com.slin.core.logger.initLogger
import org.kodein.di.*
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule

/**
 * author: slin
 * date: 2020/9/2
 * description:
 *
 */

open class CoreApplication : Application(), DIAware {

    /**
     * 依赖注入
     */
    override val di: DI by DI.lazy {
        bind<CoreApplication>() with singleton {
            this@CoreApplication
        }
        bind<AppConfig>() with singleton {
            createAppConfig(this)
        }

        import(androidCoreModule(this@CoreApplication))
        import(androidXModule(this@CoreApplication))

        import(httpClientModule)
        import(repositoryModule)

    }


    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        initLogger(BuildConfig.DEBUG)


    }

    protected open fun createAppConfig(directDIAware: DirectDIAware): AppConfig {
        return AppConfig()
    }

}
