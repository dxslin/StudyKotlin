package com.slin.study.kotlin.ui.theory

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData

/**
 * author: slin
 * date: 2021/6/23
 * description:
 *
 */
class TheoryTestActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("TouchEvent", R.drawable.img_cartoon_cat, TouchEventActivity::class.java),
        TestPageData("HandlerTest", R.drawable.img_cartoon_bear, HandlerTestActivity::class.java),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)
        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TestListFragment.newInstance(testPageDataList))
            .commit()
    }

}