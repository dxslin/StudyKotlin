package com.slin.study.kotlin.ui.test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var binding: ActivityDataBindBinding

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
        binding.apply {
            testData = HomeTestData(
                "DataBind",
                R.mipmap.cartoon_1,
                null
            )
            content = "这里是长篇内容..."
            imageId = 1
        }
        findViewById<TextView>(R.id.tv_content2).text = "复制内容：" + binding.content
    }

}