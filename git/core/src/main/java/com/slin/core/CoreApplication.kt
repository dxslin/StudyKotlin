package com.slin.core

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule


/**
 * author: slin
 * date: 2020/9/2
 * description:
 *
 */

class CoreApplication : Application(), DIAware {

    /**
     * 依赖注入
     */
    override val di: DI
        get() = DI.lazy {
            import(androidCoreModule(this@CoreApplication))
            import(androidXModule(this@CoreApplication))
        }


    override fun onCreate() {
        super.onCreate()
    }

}
