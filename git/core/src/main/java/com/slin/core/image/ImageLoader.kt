package com.slin.core.image

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