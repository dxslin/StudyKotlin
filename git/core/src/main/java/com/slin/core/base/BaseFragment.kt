package com.slin.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.x.closestDI
import org.kodein.di.diContext


/**
 * author: slin
 * date: 2020/8/28
 * description:
 *
 */

abstract class BaseFragment : Fragment(), DIAware {

    override val di: DI by closestDI()

    override val diContext: DIContext<Fragment> = diContext { this }

    protected lateinit var rootView: View

    /**
     * 布局文件id
     */
    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(layoutResId, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    protected open fun initView(view: View) {

    }


}
