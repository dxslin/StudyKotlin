package com.slin.study.kotlin.ui.transition.fragment

import android.os.Bundle
import android.view.Window
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity

class TransitionFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_fragment)
    }

    override fun applyThemeResource() {
        super.applyThemeResource()
        // 不显示ActionBar，使用SupportActionBar代替
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }
}
