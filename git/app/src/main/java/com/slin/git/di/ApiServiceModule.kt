package com.slin.git.di

import com.slin.git.api.remote.LoginService
import com.slin.git.api.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/16
 * description: http接口依赖注入module
 *
 */

@Module
@InstallIn(ApplicationComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

}
