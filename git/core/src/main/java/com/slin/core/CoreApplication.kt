package com.slin.core

import android.app.Application
import com.slin.core.config.AppConfig
import com.slin.core.config.DefaultConfig
import com.slin.core.di.httpClientModule
import com.slin.core.di.repositoryModule
import com.slin.core.logger.initLogger
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.singleton

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
            createAppConfig()
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

    protected open fun createAppConfig(): AppConfig {
        return AppConfig(
                baseUrl = DefaultConfig.BASE_URL,
                timeOutSeconds = DefaultConfig.TIME_OUT_SECONDS
        );
    }


}
