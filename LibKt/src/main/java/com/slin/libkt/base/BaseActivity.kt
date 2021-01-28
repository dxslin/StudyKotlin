package com.slin.saber.wallpaper.base

import androidx.viewbinding.ViewBinding
import com.slin.core.ui.CoreActivity
import com.slin.viewbinding.ActivityBindingDelegate


/**
 * author: slin
 * date: 2021/1/26
 * description:
 *
 */
open class BaseActivity<VB : ViewBinding> : CoreActivity() {

    protected val binding by ActivityBindingDelegate<VB>()


}
