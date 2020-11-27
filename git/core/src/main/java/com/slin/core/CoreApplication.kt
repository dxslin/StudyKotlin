package com.slin.core

import android.app.Application
import com.slin.core.config.AppConfig
import com.slin.core.di.CoreComponent
import com.slin.core.di.CoreComponentDependencies
import com.slin.core.di.DaggerCoreComponent
import com.slin.core.logger.initLogger
import dagger.hilt.android.EntryPointAccessors

/**
 * author: slin
 * date: 2020/9/2
 * description:
 *
 */
object CoreApplication {

//    companion object {
//        lateinit var INSTANCE: CoreApplication
//    }

    lateinit var appConfig: AppConfig

    lateinit var coreComponent: CoreComponent

//    override fun onCreate() {
//        super.onCreate()
//        INSTANCE = this
//        init()
//    }

    fun init(application: Application) {
        initDi(application)

        initLogger(BuildConfig.DEBUG)


    }

    private fun initDi(application: Application) {
        val dependencies = EntryPointAccessors.fromApplication(
            application,
            CoreComponentDependencies::class.java)
        appConfig = dependencies.getAppConfig()
        coreComponent = DaggerCoreComponent.builder()
            .coreDependencies(dependencies)
            .build()
        coreComponent.inject(this)

    }


}
