package com.slin.core.image.impl

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.slin.core.image.ImageConfig


/**
 * author: slin
 * date: 2020/9/14
 * description:
 *
 */
data class ImageConfigImpl(
    override val url: String,
    override val imageView: ImageView,
    override val placeholder: Int = 0,
    override val errorImage: Int = 0,
    override val width: Int = SIZE_ORIGINAL,
    override val height: Int = SIZE_ORIGINAL,

    var cacheStrategy: Int = CACHE_STRATEGY_AUTOMATIC,
    var imageRadius: Int = 0,           //图片圆角大小
    var isCircle: Boolean = false,       //是否将图片且为圆形
    val isCenterCrop: Boolean = false,   //是否由中心剪切图片
    val isCrossFade: Boolean = false,   //是否使用淡入淡出过渡动画
    val fallback: Int = 0,              //设置请求url为空时的图片

    val isClearMemory: Boolean = false,   //清理内存缓存
    val isClearDiskCache: Boolean = false,   //清理本地缓存

) : ImageConfig {

    companion object {
        /**
         * 图片大小保留原图片大小
         * @see Target.SIZE_ORIGINAL
         */
        const val SIZE_ORIGINAL = Target.SIZE_ORIGINAL

        /**
         * 缓存策略：缓存所有，包括原图片和处理之后的图片
         * @see  DiskCacheStrategy#ALL
         */
        const val CACHE_STRATEGY_ALL = 0

        /**
         * 缓存策略：不缓存任何数据
         * @see  DiskCacheStrategy#NONE
         */
        const val CACHE_STRATEGY_NONE = 1

        /**
         * 缓存策略：缓存处理之后的数据
         * @see  DiskCacheStrategy#RESOURCE
         */
        const val CACHE_STRATEGY_RESOURCE = 2

        /**
         * 缓存策略：缓存处理之前的数据，即源数据
         * @see  DiskCacheStrategy#DATA
         */
        const val CACHE_STRATEGY_DATA = 3

        /**
         * 缓存策略：自动选择缓存策略
         * @see  DiskCacheStrategy#AUTOMATIC
         */
        const val CACHE_STRATEGY_AUTOMATIC = 4
    }

}