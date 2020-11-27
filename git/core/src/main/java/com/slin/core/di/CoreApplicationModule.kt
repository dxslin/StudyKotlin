package com.slin.core.di

import android.app.Application
import com.slin.core.CoreApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 * author: slin
 * date: 2020/11/27
 * description:
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object CoreApplicationModule {

    @Provides
    fun provideCoreApplication(@ApplicationContext application: Application): CoreApplication {
        return application as CoreApplication
    }

//    @Provides
//    fun provideAppConfig(application: CoreApplication):AppConfig{
//        return application.appConfig
//    }

}