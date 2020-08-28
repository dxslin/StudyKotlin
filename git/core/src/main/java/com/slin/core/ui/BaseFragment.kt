package com.slin.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

open abstract class BaseFragment : Fragment() {


    /**
     * 布局文件id
     */
    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)


        return view
    }

    protected fun initView() {

    }


}
