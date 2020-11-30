package com.slin.core

//import com.slin.git.di.CoreComponent
import android.app.Application
import com.slin.core.config.CoreConfig
import com.slin.core.logger.initLogger
import com.slin.git.di.SlinCoreComponent
import dagger.hilt.EntryPoints

/**
 * author: slin
 * date: 2020/9/2
 * description: 基础类，封装了一些常用的第三方库
 *
 */
object SlinCore {

    lateinit var application: Application

    lateinit var coreComponent: SlinCoreComponent

    val coreConfig: CoreConfig by lazy {
        EntryPoints.get(coreComponent, CoreConfig::class.java)
//        CoreConfig(application)
    }


    fun init(application: Application, component: SlinCoreComponent) {
        this.application = application
        this.coreComponent = component
        initLogger(BuildConfig.DEBUG)
    }


}
