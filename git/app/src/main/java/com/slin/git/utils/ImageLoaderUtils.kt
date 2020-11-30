package com.slin.git.utils

import com.slin.core.SCore
import com.slin.core.image.impl.ImageConfigImpl
import com.slin.core.image.load


/**
 * author: slin
 * date: 2020/10/12
 * description:
 *
 */
object ImageLoaderUtils {
    inline fun loadImage(config: () -> ImageConfigImpl) {
        val imageLoader = SCore.coreComponent.imageLoader()
        imageLoader.load(config)
    }
}