package com.slin.study.kotlin.ui.test

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.slin.study.kotlin.R
import com.slin.study.kotlin.databinding.ActivityDataBindBinding
import com.slin.study.kotlin.ui.home.HomeTestData
import com.slin.study.kotlin.ui.home.INTENT_NAME


/**
 * author: slin
 * date: 2020/5/6
 * description:
 *
 */
class DataBindActivity : AppCompatActivity() {

    private val TAG = DataBindActivity::class.simpleName

    private lateinit var binding: ActivityDataBindBinding
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题
        intent?.extras?.getString(INTENT_NAME)?.let {
            title = it
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind)

        dataBindTest()

    }

    private fun dataBindTest() {
        val colorStateList = ColorStateList(
            arrayOf(intArrayOf(1 shl 4), intArrayOf(0)),
            intArrayOf(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent)
            )
        )
        binding.apply {
            testData = HomeTestData(
                "DataBind",
                R.mipmap.cartoon_1,
                null
            )
            content = "这里是长篇内容..."
            imageId = 1
            imageTint = colorStateList
            url = "https://www.baidu.com/img/dong_ff40776fbaec10db0dd452d55c7fe6d7.gif"
        }
        findViewById<TextView>(R.id.tv_content2).text = "复制内容：" + binding.content

        handler.postDelayed({
            binding.content = "postDelayed 3000"
        }, 3000)
    }

    fun layoutChange() {
        Log.d(TAG, "onLayoutChange")
        binding.content = "onLayoutChange"
    }


}