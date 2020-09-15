package com.slin.core.image

import android.content.Context
import com.bumptech.glide.GlideBuilder

/**
 * author: slin
 * date: 2019-04-30
 * description: Glide配置
 */
interface ApplyGlideOptions {
    fun applyGlideOptions(context: Context?, builder: GlideBuilder?)
}