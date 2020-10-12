package com.slin.core.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.Transformation
import com.slin.core.R
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
 *          imageLoader.loadImage(imageConfig)
 *  </code>
 */
class ImageLoader(private val loaderStrategy: ImageLoaderStrategy<ImageConfigImpl>) :
    ImageLoaderStrategy<ImageConfigImpl> by loaderStrategy {


}

inline fun ImageLoader.load(config: () -> ImageConfigImpl) {
    loadImage(config())
}

fun ImageLoader.load(
    imageView: ImageView,
    url: String,
    placeholder: Drawable? = ContextCompat.getDrawable(
        imageView.context,
        R.drawable.ic_image_loader_placeholder
    ),
    errorImage: Drawable? = null,
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
    transformations: List<Transformation<Bitmap>> = listOf()
) {
    loadImage(
        ImageConfigImpl(
            imageView,
            url,
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
            isClearDiskCache,
            transformations
        )
    )
}
