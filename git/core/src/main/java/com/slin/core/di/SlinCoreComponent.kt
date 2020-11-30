package com.slin.git.di

import android.app.Application
import android.content.Context
import com.slin.core.di.CoreComponentDependencies
import dagger.BindsInstance
import dagger.Component


/**
 * author: slin
 * date: 2020/11/30
 * description:
 *
 */

@Component(dependencies = [CoreComponentDependencies::class])
interface SlinCoreComponent {

    fun inject(application: Application)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun coreDependencies(coreComponentDependencies: CoreComponentDependencies): Builder
        fun build(): SlinCoreComponent
    }


}
