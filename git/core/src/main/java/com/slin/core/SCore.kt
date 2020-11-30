package com.slin.core

//import com.slin.git.di.CoreComponent
import android.app.Application
import com.slin.core.di.CoreComponentDependencies
import com.slin.core.logger.initLogger
import com.slin.git.di.DaggerSCoreComponent
import com.slin.git.di.SCoreComponent
import dagger.hilt.android.EntryPointAccessors

/**
 * author: slin
 * date: 2020/9/2
 * description: 基础类，封装了一些常用的第三方库
 *
 */
object SCore {

    lateinit var application: Application

    lateinit var coreComponent: SCoreComponent


    fun init(application: Application) {
        this.application = application
        coreComponent = DaggerSCoreComponent.builder()
            .coreDependencies(
                EntryPointAccessors.fromApplication(application,
                    CoreComponentDependencies::class.java)
            )
            .context(application)
            .build()
        coreComponent.inject(application)
        initLogger(BuildConfig.DEBUG)
    }

}
