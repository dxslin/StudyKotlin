package com.slin.study.kotlin.ui.natively

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.librarycase.kodein.KodeinCaseActivity
import com.slin.study.kotlin.ui.natively.mmap.MMapTestActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData

class NativeCaseActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("mmap", R.drawable.img_cartoon_car, MMapTestActivity::class.java),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_case)

        setShowBackButton(true)
        title = "Native"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TestListFragment.newInstance(testPageDataList))
            .commit()

    }


}