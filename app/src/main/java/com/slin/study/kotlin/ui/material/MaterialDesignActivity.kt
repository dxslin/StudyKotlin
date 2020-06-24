package com.slin.study.kotlin.ui.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.kotlin.R
import com.slin.study.kotlin.ui.home.HomeFragment
import com.slin.study.kotlin.ui.home.TestPageData

class MaterialDesignActivity : AppCompatActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("Theming", R.mipmap.cartoon_bear, MaterialThemingActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_content)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, HomeFragment.newInstance(testPageDataList))
            .commit()
    }
}