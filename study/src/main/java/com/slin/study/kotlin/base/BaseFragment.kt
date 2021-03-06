package com.slin.study.kotlin.base

import androidx.fragment.app.Fragment


/**
 * author: slin
 * date: 2020/7/7
 * description:
 *
 */
open class BaseFragment : Fragment() {

    val TAG: String by lazy {
        this::class.simpleName ?: "BaseFragment $this"
    }


}
