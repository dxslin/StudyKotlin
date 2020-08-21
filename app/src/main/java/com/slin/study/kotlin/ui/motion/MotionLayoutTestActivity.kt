package com.slin.study.kotlin.ui.motion

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.home.HomeFragment
import com.slin.study.kotlin.ui.home.TestPageData
import com.slin.study.kotlin.ui.transition.TransitionListActivity

class MotionLayoutTestActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData(
            "Simple Motion",
            R.drawable.img_cartoon_pig2,
            SimpleMotionActivity::class.java
        ),
        TestPageData("Transition", R.drawable.img_cartoon_pig1, TransitionListActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout_test)
        setShowBackButton(true)
        title = "Motion Layout Test"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, HomeFragment.newInstance(testPageDataList))
            .commit()
    }

}