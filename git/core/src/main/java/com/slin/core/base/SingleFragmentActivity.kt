package com.slin.core.base

import com.slin.core.R

/**
 * author: slin
 * date: 2020-09-07
 * description:单个fragment的activity
 */
abstract class SingleFragmentActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_single_fragment

    /**
     * fragment
     */
    abstract val contentFragment: BaseFragment

    override fun initView() {
        super.initView()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, contentFragment)
            .commit()

    }


}
