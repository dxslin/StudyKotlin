package com.slin.study.kotlin.ui.transition

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.SeekBar
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.appbar.AppBarLayout
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.BitmapUtil
import com.slin.study.kotlin.util.FastBlurUtil
import kotlinx.android.synthetic.main.activity_transition_detail.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlin.math.absoluteValue


/**
 * author: slin
 * date: 2020/8/5
 * description:
 * 1. 界面打开关联动画
 *
 */
class TransitionDetailActivity : BaseActivity() {

    companion object {
        const val INTENT_TRANSITION_DATA = "intent_transition_data"
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_detail)

        //沉浸式
        setStatusBarColor(Color.TRANSPARENT)
        //Toolbar
        setSupportActionBar(toolbar)
        setShowBackButton(true)

        val transitionData: TransitionData? = intent?.extras?.getParcelable(INTENT_TRANSITION_DATA)
        transitionData?.let {
            title = transitionData.title
            iv_pageIcon.setImageResource(transitionData.imgRes)
            tv_pageSubName.text = transitionData.abstract
            tv_pageDetail.text = transitionData.detail
        }

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val percent = (verticalOffset.toFloat() / appBarLayout.totalScrollRange).absoluteValue

        })

        iv_pageIcon.setOnClickListener {
            val intent = Intent(this, ViewLargeImageActivity::class.java)
            intent.putExtra(ViewLargeImageActivity.INTENT_LARGE_IMAGE_ID, transitionData?.imgRes)
            intent.putExtra(ViewLargeImageActivity.INTENT_LARGE_IMAGE_TITLE, "King Arthur")
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                iv_pageIcon,
                getString(R.string.key_transition_image_photo)
            )
            startActivity(intent, optionsCompat.toBundle())
        }

        sb_gaussianBlurRadius.post {
            transitionData?.let {
                var backgroundBitmap = BitmapUtil.decodeBitmap(
                    resources,
                    transitionData.imgRes,
                    appbarLayout.measuredWidth,
                    appbarLayout.measuredHeight
                )

                val scaleRatio = 1
                backgroundBitmap = BitmapUtil.cropBitmap(
                    backgroundBitmap,
                    appbarLayout.measuredWidth / scaleRatio,
                    appbarLayout.measuredHeight / scaleRatio
                )

                Log.d(
                    TAG,
                    "onCreate: backgroundBitmap width = ${backgroundBitmap.width} height = ${backgroundBitmap.height}"
                )

                sb_gaussianBlurRadius.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        Log.d(TAG, "onProgressChanged: progress = $progress")
                        FastBlurUtil.doBlurAsync(
                            backgroundBitmap,
                            progress,
                            false
                        ) { bitmap ->
                            appbarLayout.background = BitmapDrawable(resources, bitmap)
                            Log.d(
                                TAG,
                                "onCreate: width = ${bitmap.width} height = ${bitmap.height}"
                            )
                        }
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                })
            }
            sb_gaussianBlurRadius.progress = 100
        }
    }

    override fun applyThemeResource() {
        super.applyThemeResource()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}