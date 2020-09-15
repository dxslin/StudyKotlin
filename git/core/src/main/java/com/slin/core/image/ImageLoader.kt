package com.slin.core.image

import com.slin.core.image.impl.ImageConfigImpl


/**
 * author: slin
 * date: 2020/9/14
 * description: 图像加载接口，委托给ImageLoaderStrategy
 *
 */
class ImageLoader(private val loaderStrategy: ImageLoaderStrategy<ImageConfigImpl>) :
    ImageLoaderStrategy<ImageConfigImpl> by loaderStrategy {


}