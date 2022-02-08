package com.slin.study.kotlin.ui.motion

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity

class SimpleMotionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_motion)
        setShowBackButton(true)

        title = "Simple Motion"

//        val transition = motionLayout.getTransition(R.id.transition_transform)

    }
}