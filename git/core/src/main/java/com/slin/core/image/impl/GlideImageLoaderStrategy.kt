package com.slin.core.image.impl

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.slin.core.image.ImageLoaderStrategy
import com.slin.core.image.glide.GlideGit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * author: slin
 * date: 2019-04-30
 * description: 图片加载器，使用策略模式，方便切换为其他图片加载器
 */
class GlideImageLoaderStrategy : ImageLoaderStrategy<ImageConfigImpl> {

    override fun loadImage(context: Context, config: ImageConfigImpl) {
        val requests: RequestManager = GlideGit.with(context)
        // url
        var glideRequest: RequestBuilder<Drawable?> = requests.load(config.url)
        // size
        glideRequest = glideRequest.override(config.width, config.height)
        when (config.cacheStrategy) {
            ImageConfigImpl.CACHE_STRATEGY_ALL -> glideRequest.diskCacheStrategy(
                DiskCacheStrategy.ALL
            )
            ImageConfigImpl.CACHE_STRATEGY_NONE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
            ImageConfigImpl.CACHE_STRATEGY_RESOURCE -> glideRequest.diskCacheStrategy(
                DiskCacheStrategy.RESOURCE
            )
            ImageConfigImpl.CACHE_STRATEGY_DATA -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
            ImageConfigImpl.CACHE_STRATEGY_AUTOMATIC -> glideRequest.diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC
            )
            else -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
        }
        if (config.isCenterCrop) //是否由中心剪切图片
            glideRequest.centerCrop()
        if (config.isCircle) //是否将图片且为圆形
            glideRequest.circleCrop()
        if (config.isCrossFade) //是否使用淡入淡出过渡动画
            glideRequest.transition(DrawableTransitionOptions.withCrossFade())
        if (config.imageRadius > 0) //图片圆角大小
            glideRequest.transform(RoundedCorners(config.imageRadius))
        if (config.placeholder != 0) //设置占位符
            glideRequest.placeholder(config.placeholder)
        if (config.errorImage != 0) //设置错误的图片
            glideRequest.error(config.errorImage)
        if (config.fallback != 0) //设置请求url为空时的图片
            glideRequest.fallback(config.fallback)
        glideRequest.into(config.imageView)
    }

    override fun clear(context: Context, config: ImageConfigImpl) {
        GlideGit.get(context).requestManagerRetriever.get(context).clear(config.imageView)
        if (config.isClearMemory) {         //清除内存缓存
            GlobalScope.launch(Dispatchers.Main) {
                GlideGit.get(context).clearMemory()
            }
        }
        if (config.isClearDiskCache) {      //清除本地缓存
            GlobalScope.launch(Dispatchers.IO) {
                GlideGit.get(context).clearDiskCache()
            }
        }
    }
}