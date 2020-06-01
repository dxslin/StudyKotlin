package com.slin.study.kotlin.util

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide


/**
 * author: slin
 * date: 2020/6/1
 * description:
 *
 */
/**
 * 当一些方法属性和方法名不符合setter时，使用BindingMethod关联
 */
@BindingMethods(
    value = [
        BindingMethod(
            type = ImageView::class,
            attribute = "slinTint",
            method = "setImageTintList"
        )
    ]
)
object DataBindExtension {

    /**
     * 实现自己的自定义逻辑，可以接收多个参数，requireAll如果为true，则所有参数都必须指定，反之不用
     */
    @BindingAdapter(value = ["imageUrl", "error"], requireAll = false)
    @JvmStatic
    fun loadImage(view: ImageView, url: String, error: Drawable) {
        Glide.with(view).load(url).placeholder(error).into(view)
    }

    @BindingAdapter("android:onLayoutChange")
    @JvmStatic
    fun setOnLayoutChangeListener(
        view: View,
        oldValue: View.OnLayoutChangeListener?,
        newValue: View.OnLayoutChangeListener?
    ) {
        if (oldValue != null) {
            view.removeOnLayoutChangeListener(oldValue)
        }
        if (newValue != null) {
            view.addOnLayoutChangeListener(newValue)
        }
    }

    /**
     *
     */
    @BindingAdapter("text")
    @JvmStatic
    fun text(view: TextView, s: String?, newText: String?) {
        Log.d("slin", "oldText: ${s ?: ""} newText: ${newText ?: ""}")
        view.text = "oldText: ${s ?: ""} ${newText ?: ""}"
    }


}

