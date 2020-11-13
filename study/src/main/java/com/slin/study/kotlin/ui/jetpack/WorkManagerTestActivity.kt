package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityWorkManagerTestBinding

/**
 * author: slin
 * date: 2020/11/13
 * description:
 *
 */
class WorkManagerTestActivity : BaseActivity() {

    private lateinit var binding: ActivityWorkManagerTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager_test)


    }


}
