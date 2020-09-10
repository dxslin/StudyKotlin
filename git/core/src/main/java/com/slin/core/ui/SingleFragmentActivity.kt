package com.slin.core.ui

import com.slin.core.R

/**
 * author: slin
 * date: 2020-09-07
 * description:单个fragment的activity
 */
abstract class SingleFragmentActivity : CoreActivity() {

    override val layoutResId: Int = R.layout.activity_single_fragment

    /**
     * fragment
     */
    abstract val contentFragment: CoreFragment

    override fun initView() {
        super.initView()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, contentFragment)
            .commit()

    }


}
