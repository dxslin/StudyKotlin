package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData

/**
 * author: slin
 * date: 2020/11/20
 * description:JetPack组件使用
 */

class JetPackActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("ViewBind", R.drawable.img_cartoon_1, ViewBindActivity::class.java),
        TestPageData("DataBind", R.drawable.img_cartoon_2, DataBindActivity::class.java),
        TestPageData("DataStore", R.drawable.img_cartoon_car, DataStoreActivity::class.java),
        TestPageData("WorkManager",
            R.drawable.img_cartoon_cat,
            WorkManagerTestActivity::class.java),
        TestPageData("HiltInject", R.drawable.img_cartoon_3, HiltInjectActivity::class.java),
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