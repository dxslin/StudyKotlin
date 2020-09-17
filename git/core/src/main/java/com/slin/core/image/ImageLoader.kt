package com.slin.core.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.slin.core.image.impl.ImageConfigImpl


/**
 * author: slin
 * date: 2020/9/14
 * description: 图像加载接口，委托给ImageLoaderStrategy
 * sample:
 *  <code>
 *          val imageConfig = ImageConfigImpl(
 *          url = "https://avatars3.githubusercontent.com/u/12908813?v=4",
 *          imageView = iv_image,
 *          width = 300,
 *          height = 300
 *          )
 *
 *          imageLoader.loadImage(requireContext(), imageConfig)
 *  </code>
 */
class ImageLoader(private val loaderStrategy: ImageLoaderStrategy<ImageConfigImpl>) :
    ImageLoaderStrategy<ImageConfigImpl> by loaderStrategy {


}

fun ImageLoader.load(context: Context, config: () -> ImageConfigImpl) {
    loadImage(context, config())
}

fun ImageLoader.load(
    imageView: ImageView,
    url: String,
    placeholder: Drawable? = null,
    errorImage: Int = 0,
    width: Int = ImageConfigImpl.SIZE_ORIGINAL,
    height: Int = ImageConfigImpl.SIZE_ORIGINAL,

    cacheStrategy: Int = ImageConfigImpl.CACHE_STRATEGY_AUTOMATIC,
    imageRadius: Int = 0,           //图片圆角大小
    isCircle: Boolean = false,       //是否将图片且为圆形
    isCenterCrop: Boolean = false,   //是否由中心剪切图片
    isCrossFade: Boolean = false,   //是否使用淡入淡出过渡动画
    fallback: Int = 0,              //设置请求url为空时的图片

    isClearMemory: Boolean = false,   //清理内存缓存
    isClearDiskCache: Boolean = false,   //清理本地缓存)
) {
    loadImage(
        imageView.context, ImageConfigImpl(
            url,
            imageView,
            placeholder,
            errorImage,
            width,
            height,
            cacheStrategy,
            imageRadius,
            isCircle,
            isCenterCrop,
            isCrossFade,
            fallback,
            isClearMemory,
            isClearDiskCache
        )
    )
}
