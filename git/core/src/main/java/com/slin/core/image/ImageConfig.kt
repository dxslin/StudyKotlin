package com.slin.core.image

import android.graphics.drawable.Drawable
import android.widget.ImageView


/**
 * author: slin
 * date: 2020/9/14
 * description: 图像加载配置类
 *
 */
interface ImageConfig {
    val imageView: ImageView
    val url: String
    val placeholder: Drawable?
    val errorImage: Drawable?
    val width: Int
    val height: Int
}