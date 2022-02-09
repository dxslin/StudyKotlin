package com.slin.git.di

import android.app.Application
import android.content.Context
import androidx.datastore.DataStore
import com.slin.core.config.ApplyRetrofitOptions
import com.slin.core.config.CoreConfig
import com.slin.git.api.local.GitUserInfoStorage
import com.slin.git.config.Config
import com.slin.git.net.GitAuthInterceptor
import com.slin.git.net.LiveDataCallAdapterFactory
import com.slin.proto.GitUserPbOuterClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/10
 * description: app动态配置项，需要依赖注入的配置在这里
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Provides
    fun provideAppConfig(
        @ApplicationContext context: Context,
        gitUserInfoStorage: GitUserInfoStorage,
        applyRetrofitOptions: ApplyRetrofitOptions,
    ): CoreConfig {
        return CoreConfig(
            application = context as Application,
            baseUrl = Config.BASE_URL,
            httpLogLevel = HttpLoggingInterceptor.Level.BODY,
            customInterceptors = listOf<Interceptor>(
                GitAuthInterceptor(gitUserInfoStorage)
            ),
            applyRetrofitOptions = applyRetrofitOptions,
        )
    }

    @Provides
    @Singleton
    fun provideGitUserInfoStorage(
        dataStore: DataStore<GitUserPbOuterClass.GitUserPb>,
    ): GitUserInfoStorage {
        return GitUserInfoStorage.getInstance(dataStore)
    }

    @Provides
    fun provideApplyRetrofitOptions(liveDataCallAdapterFactory: LiveDataCallAdapterFactory): ApplyRetrofitOptions {
        return object : ApplyRetrofitOptions {
            override fun apply(builder: Retrofit.Builder) {
                builder.addCallAdapterFactory(liveDataCallAdapterFactory)
            }
        }
    }

}
