package com.slin.study.kotlin.ui.bottomsheet

import android.os.Bundle
import android.view.Window
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity

class BottomSheetTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_test_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BottomSheetTestFragment.newInstance())
                .commitNow()
        }
    }

    override fun applyThemeResource() {
        super.applyThemeResource()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

}