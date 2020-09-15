package com.slin.core

import android.app.Application
import com.slin.core.config.AppConfig
import com.slin.core.di.httpClientModule
import com.slin.core.di.imageLoaderModule
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

    companion object {
        lateinit var instance: CoreApplication
    }

    val appConfig by lazy {
        createAppConfig(di.direct)
    }

    /**
     * 依赖注入
     */
    override val di: DI by DI.lazy {
        bind<CoreApplication>() with singleton {
            this@CoreApplication
        }
        bind<AppConfig>() with singleton {
            appConfig
        }

        import(androidCoreModule(this@CoreApplication))
        import(androidXModule(this@CoreApplication))

        import(httpClientModule)
        import(imageLoaderModule)
        import(repositoryModule)

    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        init()
    }

    private fun init() {
        initLogger(BuildConfig.DEBUG)


    }

    /**
     * 自定义的app配置项，可以设置一些第三方参数
     * @param directDIAware kodein直接注入
     * @note 传入directDIAware是为了能够使用依赖注入来设置参数
     */
    protected open fun createAppConfig(directDIAware: DirectDIAware): AppConfig {
        return AppConfig(this)
    }


}
