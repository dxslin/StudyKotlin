package com.slin.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


/**
 * author: slin
 * date: 2021/1/27
 * description:
 *
 */

/**
 * 调用反射方法：
 * XXBinding.inflate(LayoutInflater):XXBinding
 */
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <VB : ViewBinding> Class<VB>.inflateViewBinding(
    inflater: LayoutInflater,
): VB =
    getMethod("inflate", LayoutInflater::class.java).invoke(null, inflater) as VB

/**
 * 调用反射方法：
 * XXBinding.inflate(LayoutInflater, ViewGroup, Boolean):XXBinding
 */
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <VB : ViewBinding> Class<VB>.inflateViewBinding(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToParent: Boolean,
): VB =
    getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    ).invoke(null, inflater, parent, attachToParent) as VB

/**
 * 调用
 * XXBinding.bindView(View):XXBinding
 */
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <VB : ViewBinding> Class<VB>.bindViewBinding(
    view: View,
): VB =
    getMethod("bind", View::class.java).invoke(null, view) as VB


inline fun <reified VB : ViewBinding> inflateBinding(inflater: LayoutInflater) =
    VB::class.java.inflateViewBinding(inflater)


inline fun <reified VB : ViewBinding> inflateBinding(
    inflater: LayoutInflater,
    parent: ViewGroup,
    attachToParent: Boolean,
) = VB::class.java.inflateViewBinding(inflater, parent, attachToParent)


inline fun <reified VB : ViewBinding> bindViewBinding(view: View) =
    VB::class.java.bindViewBinding(view)

