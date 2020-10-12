package com.slin.core.image


/**
 * author: slin
 * date: 2020/9/14
 * description: 图像加载策略
 *
 */
interface ImageLoaderStrategy<T : ImageConfig> {

    fun loadImage(config: T)

    fun clear(config: T)

}