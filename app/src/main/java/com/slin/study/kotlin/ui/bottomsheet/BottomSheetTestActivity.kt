package com.slin.study.kotlin.ui.bottomsheet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.kotlin.R

class BottomSheetTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet_test_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, BottomSheetTestFragment.newInstance())
                .commitNow()
        }
    }
}