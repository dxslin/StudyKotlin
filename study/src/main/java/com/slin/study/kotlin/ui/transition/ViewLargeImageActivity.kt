package com.slin.study.kotlin.ui.transition

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.BitmapUtil
import com.slin.study.kotlin.util.FastBlurUtil
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
        imageResId?.let {
            fl_content.post {
                iv_center.setImageResource(it)
                val scale = 10
                val bitmap = BitmapUtil.decodeBitmap(
                    resources,
                    imageResId,
                    fl_content.measuredWidth / scale,
                    fl_content.measuredHeight / scale
                )
                FastBlurUtil.doBlurAsync(bitmap, 80, false) { result ->
                    fl_content.background = BitmapDrawable(resources, result)
                    Log.d(TAG, "onCreate: width = ${result.width} height = ${result.height}")
                }

            }
        }

        val titleStr = intent?.extras?.getString(INTENT_LARGE_IMAGE_TITLE)
        title = if (!titleStr.isNullOrBlank()) {
            titleStr
        } else {
            "Large Image"
        }

        fl_content.setOnClickListener {
            finishAfterTransition()
        }
    }


}