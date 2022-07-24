package com.slin.study.kotlin.base

import androidx.fragment.app.Fragment

/**
 * author: slin
 * date: 2020/7/7
 * description:
 *
 */
open class BaseFragment : Fragment {

    constructor(layoutRes: Int = 0) : super(layoutRes)

    constructor() : super()

    val TAG: String by lazy {
        this::class.simpleName ?: "BaseFragment $this"
    }
}
