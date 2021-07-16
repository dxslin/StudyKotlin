package com.slin.study.kotlin.ui.theory

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityTouchEventBinding
import com.slin.study.kotlin.ui.home.testDataList
import com.slin.study.kotlin.ui.testlist.TestPageData
import com.slin.study.kotlin.util.Logger

/**
 * author: slin
 * date: 2021/6/23
 * description: 测试点击事件
 *
 */
class TouchEventActivity : BaseActivity() {


    private lateinit var binding: ActivityTouchEventBinding

    private val list = mutableListOf<TestPageData>().apply {
        for (i in 0..1000) {
            addAll(testDataList)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.log(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityTouchEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setShowBackButton(true)
        title = "TouceEvent"
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.apply {
            /**
             * 当滑动的时候，如果控件滑动到外面之后，setPressed(false)将 mPrivateFlags的PFLAG_PRESSED位置0,
             * ACTION_UP时会判断mPrivateFlags & PFLAG_PRESSED，如果之前被置0就不会执行onClick了
             *
             *
             */
            btnButton1.setOnTouchListener { v, event ->
                Logger.log(TAG, "touch event: ${event.action} v:$v")
                false
            }

            btnButton2.setOnTouchListener { v, event ->
                Logger.log(TAG, "touch event: ${event.action} v:$v")
                false
            }

            btnButton1.setOnClickListener {
                Logger.log(TAG, "click button1")
            }
            btnButton2.setOnClickListener {
                Logger.log(TAG, "click button2")
            }
            btnButton3.setOnClickListener {

                throw RuntimeException("bbb")

//                    list.addAll(list)
//                rvRecyclerView.adapter?.notifyDataSetChanged()
            }

            rvRecyclerView.layoutManager = LinearLayoutManager(this@TouchEventActivity)
            rvRecyclerView.adapter = object : BaseQuickAdapter<TestPageData, BaseViewHolder>(
                R.layout.item_image_text,
                list
            ) {
                override fun convert(holder: BaseViewHolder, item: TestPageData) {
                    holder.getView<ImageView>(R.id.iv_icon).setImageResource(item.icon)
                    holder.getView<TextView>(R.id.tv_title).text = item.name

                    holder.itemView.setOnTouchListener { v, event ->
                        Logger.log(TAG, "item touch event: ${event.action} v:$v")
                        false
                    }
                    holder.itemView.setOnClickListener {
                        Logger.log(TAG, "item click $item")
                    }
                }
            }

        }
    }

    override fun onStart() {
        Logger.log(TAG, "onStart")
        super.onStart()
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

            }

        }, IntentFilter("xx"))

    }

    override fun onRestart() {
        Logger.log(TAG, "onRestart")
        super.onRestart()
    }

    override fun onResume() {
        Logger.log(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Logger.log(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Logger.log(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Logger.log(TAG, "onDestroy")
        super.onDestroy()
    }

}