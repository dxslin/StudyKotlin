package com.slin.study.kotlin.ui.material

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData

class MaterialDesignActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("Theming", R.drawable.img_cartoon_bear, MaterialThemingActivity::class.java),
        TestPageData("Theme", R.drawable.img_cartoon_pig2, MaterialThemeActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_content)
        setShowBackButton(true)
        title = "MaterialDesign"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TestListFragment.newInstance(testPageDataList))
            .commit()
    }


}