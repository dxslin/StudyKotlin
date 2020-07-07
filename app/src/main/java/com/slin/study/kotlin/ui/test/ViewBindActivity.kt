package com.slin.study.kotlin.ui.test

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityViewBindBinding
import com.slin.study.kotlin.ui.home.INTENT_NAME

class ViewBindActivity : BaseActivity() {

    private lateinit var binding: ActivityViewBindBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题
        intent?.extras?.getString(INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)
        //获取ViewBinding
        binding = ActivityViewBindBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        viewBindingTest()
    }

    /**
     * 访问ViewBinding里面的控件试试
     */
    private fun viewBindingTest() {
        binding.tvHome.text = " View Bind"
        binding.tvTextBind.apply {
            text = "View bind test"
            setOnClickListener { _ ->
                text = "bind view clicked"
            }
        }

        //尝试访问include包含的布局控件
        binding.llHomeContent.tvHomeContent.apply {
            text = "1111"
            setTextColor(Color.WHITE)
        }
        binding.llHomeContent.root.setBackgroundColor(Color.GRAY)

    }

}
