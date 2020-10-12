package com.slin.git.utils

import com.slin.core.image.ImageLoader
import com.slin.core.image.impl.ImageConfigImpl
import com.slin.core.image.load
import com.slin.git.SlinGitApplication
import org.kodein.di.instance


/**
 * author: slin
 * date: 2020/10/12
 * description:
 *
 */
object ImageLoaderUtils {
    inline fun loadImage(config: () -> ImageConfigImpl) {
        val imageLoader by SlinGitApplication.INSTANCE.di.instance<ImageLoader>()
        imageLoader.load(config)
    }
}