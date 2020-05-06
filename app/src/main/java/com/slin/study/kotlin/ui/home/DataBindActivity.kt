package com.slin.study.kotlin.ui.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.slin.study.kotlin.R
import com.slin.study.kotlin.databinding.ActivityDataBindBinding


/**
 * author: slin
 * date: 2020/5/6
 * description:
 *
 */
class DataBindActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataBindBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_data_bind)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind)

    }

    private fun dataBindTest() {
        binding.apply {
            testData = HomeTestData("DataBind", R.mipmap.cartoon_1, null)


        }
    }

}