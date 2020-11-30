package com.slin.core.di

import com.google.gson.Gson
import com.slin.core.config.CoreConfig
import com.slin.core.logger.logd
import com.slin.core.net.ResultsCallAdapterFactory
import com.slin.core.net.RxResultsCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


/**
 * author: slin
 * date: 2020/9/2
 * description: Http 网络服务注入module
 *
 */

@Module
@InstallIn(ApplicationComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        builder: Retrofit.Builder,
        @OkHttpClientQualifier
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        rxResultsCallAdapterFactory: RxResultsCallAdapterFactory,
        resultsCallAdapterFactory: ResultsCallAdapterFactory,
        config: CoreConfig,
    ): Retrofit {
        builder
            .baseUrl(config.baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxResultsCallAdapterFactory)
            .addCallAdapterFactory(resultsCallAdapterFactory)
        config.applyRetrofitOptions?.apply(builder)
        return builder.build()
    }

    @Singleton
    @Provides
    @OkHttpClientQualifier
    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
        loggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher,
        config: CoreConfig,
    ): OkHttpClient {
        okHttpClientBuilder
            .connectTimeout(config.timeOutSeconds, TimeUnit.SECONDS)
            .readTimeout(config.timeOutSeconds, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .addInterceptor(loggingInterceptor)
        val interceptors = config.customInterceptors
        interceptors?.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        config.applyOkHttpOptions?.apply(okHttpClientBuilder)

        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideDispatcher(config: CoreConfig): Dispatcher {
        return Dispatcher(config.executorService)
    }

    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Provides
    fun provideHttpLoggingInterceptor(coreConfig: CoreConfig): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logd { "http: $message" }
            }
        }).apply {
            level = coreConfig.httpLogLevel
        }
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientQualifier

