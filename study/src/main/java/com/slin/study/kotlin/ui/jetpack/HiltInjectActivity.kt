package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.RequestManager
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityHiltInjectBinding
import com.slin.study.kotlin.ui.testlist.TestListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * author: slin
 * date: 2020/11/20
 * description: Hilt 使用测试
 * 1. Application -> add @HiltAndroidApp annotation  activity -> add @AndroidEntryPoint
 * 2. Create module -> add @Module & @InstallIn(XXComponent) -> create function -> add @Binds/@Provides
 * 3. inject to variable which cannot be private
 * 4. You need add `implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"`
 *      and `kapt "androidx.hilt:hilt-compiler:1.0.0-alpha02"` if use hilt in ViewModel
 * 5. Use `@ActivityContext` or `@ApplicationContext` inject context if you want
 *
 * 参考文档：https://developer.android.google.cn/training/dependency-injection/hilt-android
 */

@AndroidEntryPoint
class HiltInjectActivity : BaseActivity() {


    private val viewModel by viewModels<HiltInjectViewModel>()


    private lateinit var binding: ActivityHiltInjectBinding

    @Inject
    lateinit var imgRequestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hilt_inject)

        initView()
    }

    private fun initView() {
        binding.apply {
            viewModel.result.observe(this@HiltInjectActivity, {
                tvViewModelInject.text = it
            })

            imgRequestManager.asGif()
                .load("https://www.baidu.com/img/dong_ff40776fbaec10db0dd452d55c7fe6d7.gif")
                .into(ivModuleInject)

            btnAnalyticsService.setOnClickListener { viewModel.analyticsServiceResult() }
            btnAnalyticsServiceOther.setOnClickListener { viewModel.analyticsServiceOtherResult() }
            btnRequestInfo.setOnClickListener { viewModel.requestUserInfo() }


        }

    }


}