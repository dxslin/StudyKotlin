package com.slin.core.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

open class CoreFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutId: Int) : super(layoutId)

}
