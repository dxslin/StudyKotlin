package com.slin.core.image.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.slin.core.config.AppConfig
import com.slin.core.ext.getAppConfig
import com.slin.core.image.ApplyGlideOptions
import okhttp3.OkHttpClient
import java.io.File
import java.io.InputStream

/**
 * author: slin
 * date: 2019-04-29
 * description:
 */
@GlideModule(glideName = "GlideGit")
class GlideConfiguration : AppGlideModule() {

    //    private val okHttpClient = CoreApplication.INSTANCE.coreComponent.imageOkHttpClient()
    private val okHttpClient = OkHttpClient.Builder().build()

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val appConfig: AppConfig = context.getAppConfig()
        builder.setDiskCache {
            DiskLruCacheWrapper.create(
                File(appConfig.cacheFile, "Glide"),
                IMAGE_DISK_CACHE_MAX_SIZE.toLong()
            )
        }
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(
            LruResourceCache(
                customMemoryCacheSize.toLong()
            )
        )
        builder.setBitmapPool(
            LruBitmapPool(
                customBitmapPoolSize.toLong()
            )
        )

        //自定义Glide的配置
        val applyGlideOptions: ApplyGlideOptions? = appConfig.glideOptions
        applyGlideOptions?.applyGlideOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        //使用OkHttp替换掉Glide原有的HttpURLConnection做网络请求
//        val okHttpClient: OkHttpClient by CoreApplication.INSTANCE.di.instance(
//            IMAGE_OK_HTTP_CLIENT_TAG
//        )
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpGlideUrlLoader.Factory(okHttpClient)
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        private const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024 //图片缓存文件最多能存100MB
    }

}