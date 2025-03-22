package com.slin.study.kotlin.ui.transition.fgswicth

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity

class FragmentSwitchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_switch)
        title = "Fragment Anim"
        setShowBackButton(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, SwitchOneFragment.newInstance(SwitchOneFragment.PAGE_ONE))
            .commit()
    }


}
