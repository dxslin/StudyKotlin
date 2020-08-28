package com.slin.study.kotlin.ui.material

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.home.HomeFragment
import com.slin.study.kotlin.ui.home.TestPageData
import com.slin.study.kotlin.ui.transition.TransitionListActivity

class MaterialDesignActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("Theming", R.drawable.img_cartoon_bear, MaterialThemingActivity::class.java),
        TestPageData("Transition", R.drawable.img_cartoon_pig1, TransitionListActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_content)
        setShowBackButton(true)
        title = "MaterialDesign"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, HomeFragment.newInstance(testPageDataList))
            .commit()
    }


}