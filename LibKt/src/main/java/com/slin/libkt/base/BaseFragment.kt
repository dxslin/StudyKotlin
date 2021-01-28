package com.slin.saber.wallpaper.base

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.slin.core.ui.CoreFragment
import com.slin.viewbinding.FragmentBindingDelegate


/**
 * author: slin
 * date: 2021/1/27
 * description:
 *
 */
open class BaseFragment<VB : ViewBinding>(@LayoutRes layoutId: Int) : CoreFragment(layoutId) {

    protected val binding: VB by FragmentBindingDelegate()


}