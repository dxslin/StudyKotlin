package com.slin.git

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.slin.core.CoreApplication
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

        Handler(Looper.getMainLooper())
            .post {

                CoreApplication.init(this)
            }
    }

//    override fun createAppConfig(): AppConfig {
//        return super.createAppConfig().copy(
//                baseUrl = Config.BASE_URL,
//                httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
//                customInterceptors = instance(),
//                applyRetrofitOptions = instance()
//            )

//    }
}
