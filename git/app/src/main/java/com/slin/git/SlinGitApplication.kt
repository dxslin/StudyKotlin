package com.slin.git

import android.app.Application
import com.google.gson.Gson
import com.slin.core.SlinCore
import com.slin.core.config.CoreConfig
import com.slin.core.config.DefaultConfig
import com.slin.core.di.CoreComponentDependencies
import com.slin.git.config.Config
import com.slin.git.di.DaggerSlinCoreComponent
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * author: slin
 * date: 2020/9/4
 * description:
 *
 */
@HiltAndroidApp
class SlinGitApplication : Application() {

    companion object {
        lateinit var INSTANCE: SlinGitApplication
    }

    @Inject
    lateinit var gson: Gson

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        val appConfig = CoreConfig(
            application = this,
            baseUrl = Config.BASE_URL,
            httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL
        )


        val coreComponent = DaggerSlinCoreComponent.builder()
            .coreDependencies(
                EntryPointAccessors.fromApplication(this, CoreComponentDependencies::class.java)
            )
            .context(this)
            .build()

        SlinCore.init(this, coreComponent)
        coreComponent.inject(this)
    }


}

