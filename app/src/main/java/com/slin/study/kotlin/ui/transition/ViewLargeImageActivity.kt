package com.slin.study.kotlin.ui.transition

import android.os.Bundle
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_large_image.*

/**
 * author: slin
 * date: 2020/8/5
 * description:
 * 1. 界面打开关联动画
 *
 */
class ViewLargeImageActivity : BaseActivity() {

    companion object {
        const val INTENT_LARGE_IMAGE_ID = "intent_image_id"
        const val INTENT_LARGE_IMAGE_TITLE = "intent_large_image_title"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_large_image)

        setShowBackButton(true)


        val imageResId = intent?.extras?.getInt(INTENT_LARGE_IMAGE_ID)
        imageResId?.let { iv_center.setImageResource(it) }

        val titleStr = intent?.extras?.getString(INTENT_LARGE_IMAGE_TITLE)
        title = if (!titleStr.isNullOrBlank()) {
            titleStr
        } else {
            "Large Image"
        }

    }
}