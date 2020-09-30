package com.slin.libkt.ui.svs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slin.libkt.R

class SvsTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_svs_test)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_content, SvsTestFragment.newInstance())
            .commit()
    }
}