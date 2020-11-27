package com.slin.core.di

import android.content.Context
import com.slin.core.CoreApplication
import com.slin.core.config.AppConfig
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


/**
 * author: slin
 * date: 2020/11/27
 * description:
 *
 */
@EntryPoint
@InstallIn(ApplicationComponent::class)
interface CoreComponentDependencies {

    fun getAppConfig(): AppConfig


}


@Component(dependencies = [CoreComponentDependencies::class])
interface CoreComponent {

    fun inject(application: CoreApplication)

//    fun gson():Gson
//
//    @ImageOkHttpClientQualifier
//    fun imageOkHttpClient():OkHttpClient

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun coreDependencies(coreComponentDependencies: CoreComponentDependencies): Builder
        fun build(): CoreComponent
    }


}
