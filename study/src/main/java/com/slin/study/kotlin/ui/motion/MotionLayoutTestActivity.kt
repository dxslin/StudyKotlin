package com.slin.study.kotlin.ui.motion

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData
import com.slin.study.kotlin.ui.transition.LayoutTransitionActivity
import com.slin.study.kotlin.ui.transition.TransitionListActivity
import com.slin.study.kotlin.ui.transition.fgswicth.FragmentSwitchActivity
import com.slin.study.kotlin.ui.transition.fragment.TransitionFragmentActivity

class MotionLayoutTestActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData(
            "Simple Motion",
            R.drawable.img_cartoon_car,
            SimpleMotionActivity::class.java
        ),
        TestPageData(
            "List Page Transition",
            R.drawable.img_cartoon_pig1,
            TransitionListActivity::class.java
        ),
        TestPageData(
            "Layout Transition",
            R.drawable.img_cartoon_pig2,
            LayoutTransitionActivity::class.java
        ),
        TestPageData(
            "Fragment Transition",
            R.drawable.img_cartoon_2,
            TransitionFragmentActivity::class.java
        ),
        TestPageData(
            "Fragment Switch",
            R.drawable.img_cartoon_3,
            FragmentSwitchActivity::class.java
        ),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout_test)
        setShowBackButton(true)
        title = "Motion Layout Test"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TestListFragment.newInstance(testPageDataList))
            .commit()
    }

}