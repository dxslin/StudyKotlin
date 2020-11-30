package com.slin.core.di

import android.app.Application
import com.slin.core.SlinCore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier


/**
 * author: slin
 * date: 2020/11/27
 * description:
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object SlinCoreModule {

    @Provides
    fun provideSlinCore(): SlinCore {
        return SlinCore
    }

    @Provides
    @SlinCoreApplicationQualifier
    fun provideApplication(slinCore: SlinCore): Application {
        return slinCore.application
    }

//    @Provides
//    fun provideAppConfig(slinCore:SlinCore): AppConfig {
//        return slinCore.appConfig
//    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SlinCoreApplicationQualifier
