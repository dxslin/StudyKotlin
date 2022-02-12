package com.slin.study.kotlin.ui.librarycase

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.ui.librarycase.kodein.KodeinCaseActivity
import com.slin.study.kotlin.ui.librarycase.matrix.MatrixTestActivity
import com.slin.study.kotlin.ui.testlist.TestListFragment
import com.slin.study.kotlin.ui.testlist.TestPageData

class LibraryCaseActivity : BaseActivity() {

    private val testPageDataList: ArrayList<TestPageData> = arrayListOf(
        TestPageData("Kodein", R.drawable.img_kodein_di_logo, KodeinCaseActivity::class.java),
        TestPageData("Matrix", R.drawable.img_matrix_logo, MatrixTestActivity::class.java)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_case)

        setShowBackButton(true)
        title = "Library Case"
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, TestListFragment.newInstance(testPageDataList))
            .commit()

    }


}