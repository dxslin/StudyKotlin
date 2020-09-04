package com.slin.git

import com.slin.core.CoreApplication
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import org.kodein.di.subDI


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
    }

    override fun onCreate() {
        super.onCreate()
    }

}
