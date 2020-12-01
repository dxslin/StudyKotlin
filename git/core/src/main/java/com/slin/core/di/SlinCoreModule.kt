package com.slin.core.di

import android.app.Application
import android.content.Context
import com.slin.core.SCore
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
    fun provideSCore(): SCore {
        return SCore
    }

    @Provides
    @SCoreApplicationQualifier
    fun provideApplication(slinCore: SCore): Application {
        return slinCore.application
    }

    @Provides
    @SCoreContextQualifier
    fun provideCoreContext(@SCoreApplicationQualifier application: Application): Context {
        return application.applicationContext
    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SCoreApplicationQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SCoreContextQualifier
