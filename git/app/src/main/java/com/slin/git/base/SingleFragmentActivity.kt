package com.slin.git.base

import android.os.Bundle
import com.slin.core.ui.CoreActivity
import com.slin.core.ui.CoreFragment
import com.slin.git.R

/**
 * author: slin
 * date: 2020-09-07
 * description:单个fragment的activity
 */
abstract class SingleFragmentActivity : CoreActivity() {

    /**
     * fragment
     */
    abstract val contentFragment: CoreFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, contentFragment)
            .commit()
    }


}
