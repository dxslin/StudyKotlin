package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityWorkManagerTestBinding
import com.slin.study.kotlin.ui.testlist.TestListFragment

/**
 * author: slin
 * date: 2020/11/13
 * description:
 *
 */
class WorkManagerTestActivity : BaseActivity() {

    private lateinit var binding: ActivityWorkManagerTestBinding

    private val viewModel: WorkManagerTestViewModel by lazy {
        WorkManagerTestViewModel(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager_test)


        initView()

    }

    private fun initView() {
        binding.apply {
            viewModel.workResult.observe(this@WorkManagerTestActivity, Observer {
                tvWorkResult.text = it
            })
            btnOnceRequest.setOnClickListener { viewModel.onceRequestTest() }
            btnPeriodRequest.setOnClickListener { viewModel.periodRequestTest() }
            btnDownloadRequest.setOnClickListener { viewModel.downloadWorkTest() }
        }
    }


}












