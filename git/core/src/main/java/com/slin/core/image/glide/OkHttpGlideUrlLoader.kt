package com.slin.core.image.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * author: slin
 * date: 2019-04-29
 * description: 替换Glide原有的http加载库
 */
class OkHttpGlideUrlLoader(private val okHttpClient: OkHttpClient) :
    ModelLoader<GlideUrl, InputStream> {
    override fun buildLoadData(
        glideUrl: GlideUrl,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(glideUrl, OkHttpFetcher(okHttpClient, glideUrl))
    }

    override fun handles(glideUrl: GlideUrl): Boolean {
        return true
    }

    class Factory(private var okHttpClient: OkHttpClient?) :
        ModelLoaderFactory<GlideUrl, InputStream> {
        @Synchronized
        private fun getOkHttpClient(): OkHttpClient {
            if (okHttpClient == null) {
                okHttpClient = OkHttpClient()
            }
            return okHttpClient!!
        }

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkHttpGlideUrlLoader(getOkHttpClient())
        }

        override fun teardown() {}
    }
}