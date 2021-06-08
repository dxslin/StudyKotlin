package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityVmLiveDataBinding
import com.slin.study.kotlin.util.Logger

/**
 * author: slin
 * date: 2021/6/8
 * description:
 * 测试ViewModel & LiveData
 * 1. ViewModel是如何在旋转屏幕的Activity销毁时保持数据不变的？
 * 2. LiveData连续两次提交数据，Observer会执行几次？
 * 3. LiveData第一次订阅是否会收到数据，如果不想收到数据该如何处理？
 *
 */
class VMLiveDataActivity : BaseActivity() {

    val tag = this.javaClass.simpleName

    private lateinit var vmLiveDataBinding: ActivityVmLiveDataBinding

    //    private val viewModel by viewModels<VMLiveDataViewModel>()
    private val viewModel =
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get<VMLiveDataViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setShowBackButton(true)

        vmLiveDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_vm_live_data)


        vmLiveDataBinding.apply {
            vm = viewModel
            btnButton1.setOnClickListener {
                viewModel.name.postValue("slin")
                viewModel.name.postValue("slin")
            }

            btnButton2.setOnClickListener {
                viewModel.name.value = "dxslin"
                viewModel.name.value = "dxslin"

            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.name.observe(this@VMLiveDataActivity, Observer {
                viewModel.count.value = viewModel.count.value?.plus(1)
                vmLiveDataBinding.vm = viewModel
                Logger.log(TAG, "$viewModel, name change: $it, count: ${viewModel.count.value} ")
            })
        }

    }


}