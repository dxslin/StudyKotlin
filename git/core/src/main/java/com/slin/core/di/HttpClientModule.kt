package com.slin.core.di

import com.google.gson.Gson
import com.slin.core.config.AppConfig
import com.slin.core.logger.logd
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/9/2
 * description: Http 网络服务注入module
 *
 */

const val HTTP_CLIENT_MODULE_TAG = "http_client_module_tag"


val httpClientModule = DI.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit>() with singleton {
        val builder = instance<Retrofit.Builder>()
            .baseUrl(instance<AppConfig>().baseUrl)
            .client(instance())
            .addConverterFactory(instance<GsonConverterFactory>())
        instance<AppConfig>().applyRetrofitOptions?.apply(builder)

        builder.build()
    }

    bind<OkHttpClient>() with singleton {
        val config = instance<AppConfig>()
        val okHttpClientBuilder = instance<OkHttpClient.Builder>()
            .connectTimeout(instance<AppConfig>().timeOutSeconds, TimeUnit.SECONDS)
            .readTimeout(instance<AppConfig>().timeOutSeconds, TimeUnit.SECONDS)
            .dispatcher(Dispatcher(instance<AppConfig>().executorService))
            .addInterceptor(instance<HttpLoggingInterceptor>())
        val interceptors = config.customInterceptors
        interceptors?.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        config.applyOkHttpOptions?.apply(okHttpClientBuilder)

        okHttpClientBuilder.build()
    }

    bind<OkHttpClient.Builder>() with provider {
        OkHttpClient.Builder()
    }

    bind<Retrofit.Builder>() with provider {
        Retrofit.Builder()
    }

    bind<HttpLoggingInterceptor>() with provider {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logd { "http: $message" }
            }
        }).apply {
            level = instance<AppConfig>().httpLogLevel
        }
    }

    bind<Gson>() with singleton {
        Gson()
    }

    bind<GsonConverterFactory>() with provider {
        GsonConverterFactory.create(instance())
    }

}


