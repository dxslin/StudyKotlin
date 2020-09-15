package com.slin.core.di

import com.slin.core.config.AppConfig
import com.slin.core.config.Constants.GLOBAL_TAG
import com.slin.core.image.ImageLoader
import com.slin.core.image.ImageLoaderStrategy
import com.slin.core.image.impl.GlideImageLoaderStrategy
import com.slin.core.image.impl.ImageConfigImpl
import com.slin.core.logger.logd
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */

const val IMAGE_LOADER_MODULE_TAG = "image_loader_module_tag"

const val IMAGE_OK_HTTP_CLIENT_TAG = "image_ok_http_client_tag"
const val IMAGE_HTTP_LOGGING_INTERCEPTOR_TAG = "image_http_logging_interceptor_tag"

val imageLoaderModule = DI.Module(IMAGE_LOADER_MODULE_TAG) {

    bind<ImageLoader>() with singleton {
        ImageLoader(instance())
    }

    bind<ImageLoaderStrategy<ImageConfigImpl>>() with singleton {
        GlideImageLoaderStrategy()
    }


    bind<OkHttpClient>(IMAGE_OK_HTTP_CLIENT_TAG) with singleton {
        instance<OkHttpClient.Builder>()
            .dispatcher(Dispatcher(instance<AppConfig>().executorService))
            .addInterceptor(instance<HttpLoggingInterceptor>(IMAGE_HTTP_LOGGING_INTERCEPTOR_TAG))
            .build()
    }

    bind<HttpLoggingInterceptor>(IMAGE_HTTP_LOGGING_INTERCEPTOR_TAG) with provider {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logd { "${GLOBAL_TAG}_glide: $message" }
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

}
