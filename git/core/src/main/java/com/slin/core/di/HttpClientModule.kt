package com.slin.core.di

import com.google.gson.Gson
import com.slin.core.BuildConfig
import com.slin.core.config.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/9/2
 * description: Http 服务注入module
 *
 */

const val HTTP_CLIENT_MODULE_TAG = "http_client_module_tag"


val httpClientModule = DI.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(instance<AppConfig>().baseUrl)
            .client(instance())
            .addConverterFactory(instance())
            .build()
    }

    bind<OkHttpClient>() with singleton {

        val okHttpClientBuilder = instance<OkHttpClient.Builder>()
                .connectTimeout(instance<AppConfig>().timeOutSeconds, TimeUnit.SECONDS)
                .readTimeout(instance<AppConfig>().timeOutSeconds, TimeUnit.SECONDS)
                .addInterceptor(instance<HttpLoggingInterceptor>())
        val interceptors = instance<List<Interceptor>>()
        interceptors.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }

        okHttpClientBuilder.build()
    }

    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
    }

    bind<Retrofit.Builder>() with provider {
        Retrofit.Builder()
    }

    bind<HttpLoggingInterceptor>() with provider {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    bind<Gson>() with singleton {
        Gson()
    }

    bind<GsonConverterFactory>() with provider {
        GsonConverterFactory.create(instance())
    }

}


