package com.slin.core.di

import android.app.Application
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
    @SlinCoreApplicationQualifier
    fun provideApplication(slinCore: SCore): Application {
        return slinCore.application
    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SlinCoreApplicationQualifier
