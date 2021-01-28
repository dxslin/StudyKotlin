package com.slin.viewbinding

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


/**
 * author: slin
 * date: 2021/1/26
 * description: View Binding 扩展类
 *
 */

/**
 * 创建 ViewBinding
 *
 * ```
 * private val bind:ActivityXXXBinding by viewBinding()
 * ```
 *
 */
inline fun <reified VB : ViewBinding> Activity.viewBinding() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

/**
 * 创建 ViewBinding
 *
 * 这个是生成自定义的委托类，建议使用[Activity.viewBinding]
 *
 * ```
 * private val bind:ActivityXXXBinding by viewBindingDelegate()
 * ```
 *
 */
inline fun <reified VB : ViewBinding> Activity.viewBindingDelegate() =
    ActivityBindingDelegate(VB::class.java)

/**
 * 创建 ViewBinding
 *
 * ```
 * private val bind:DialogXXXBinding by viewBinding()
 * ```
 *
 */
inline fun <reified VB : ViewBinding> Dialog.viewBinding() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

/**
 * 创建 ViewBinding
 *
 */
inline fun <reified VB : ViewBinding> ViewGroup.viewBinding(attachToParent: Boolean = false) =
    inflateBinding<VB>(LayoutInflater.from(context), this, attachToParent)

/**
 * 创建 ViewBinding
 *
 * 这个是生成自定义的委托类，建议使用[Activity.viewBinding]
 *
 * ```
 * private val bind:FragmentXXXBinding by viewBinding()
 * ```
 *
 */
inline fun <reified VB : ViewBinding> Fragment.viewBinding() =
    FragmentBindingDelegate(VB::class.java)

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

inline fun <reified VB : ViewBinding> newBindingViewHolder(parent: ViewGroup): BindingViewHolder<VB> {
    return BindingViewHolder(parent.viewBinding(false))
}
