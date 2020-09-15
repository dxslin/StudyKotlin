package com.slin.core.image

import android.content.Context


/**
 * author: slin
 * date: 2020/9/14
 * description: 图像加载策略
 *
 */
interface ImageLoaderStrategy<T : ImageConfig> {

    fun loadImage(context: Context, config: T)

    fun clear(context: Context, config: T)

}