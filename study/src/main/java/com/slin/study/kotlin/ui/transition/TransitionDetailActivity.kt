package com.slin.study.kotlin.ui.transition

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.transition.*
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SeekBar
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.appbar.AppBarLayout
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.util.BitmapUtil
import com.slin.study.kotlin.util.FastBlurUtil
import com.slin.study.kotlin.util.Logger
import kotlinx.android.synthetic.main.activity_transition_detail.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
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

        //共享元素的动画
        val changeBounds = ChangeBounds()
        changeBounds.duration = 800
        window?.sharedElementEnterTransition = changeBounds
        window?.sharedElementExitTransition = changeBounds

        //界面切换的过渡动画
//        val slide = Slide()
//        val explode = Explode()
        val fade = Fade()
        fade.duration = 500
        window?.enterTransition = fade
        window?.exitTransition = fade

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
//            val  optionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(
//                iv_pageIcon,
//                iv_pageIcon.width,
//                iv_pageIcon.height,
//                0,
//                0
//            )
            Log.d(
                TAG,
                "onCreate: ${(iv_pageIcon.left + iv_pageIcon.right) / 2}  ${(iv_pageIcon.top + iv_pageIcon.bottom) / 2}"
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

                Logger.log(
                    TAG,
                    "onCreate: backgroundBitmap width = ${backgroundBitmap.width} height = ${backgroundBitmap.height}"
                )

                val channel = Channel<Int>()
                GlobalScope.launch(Dispatchers.IO) {
                    channel.consumeAsFlow()
                        .debounce(50)
                        .collect { progress ->
                            Logger.log(TAG, "channel collect: $progress")
                            //这里直接使用协程来
                            val bitmap = FastBlurUtil.doBlur(backgroundBitmap, progress, false)
                            withContext(Dispatchers.Main) {
                                appbarLayout.background = BitmapDrawable(resources, bitmap)
                            }

                            //使用线程处理
//                            FastBlurUtil.doBlurAsync(
//                                backgroundBitmap,
//                                progress,
//                                false
//                            ) { bitmap ->
//                                appbarLayout.background = BitmapDrawable(resources, bitmap)
//                                Logger.log(
//                                    TAG,
//                                    "onCreate: width = ${bitmap.width} height = ${bitmap.height}"
//                                )
//                            }
                        }

                    channel.offer(sb_gaussianBlurRadius.progress)
                    Logger.log(TAG, "channel offer: ${sb_gaussianBlurRadius.progress}")
                }
                sb_gaussianBlurRadius.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        Logger.log(TAG, "onProgressChanged: progress = $progress")
                        channel.offer(progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                })
                sb_gaussianBlurRadius.post {
                    sb_gaussianBlurRadius.progress = 40
                }
            }
        }
    }

    fun textChange(view: View) {
        val transitionSet = TransitionSet()
        transitionSet.ordering = TransitionSet.ORDERING_TOGETHER
        transitionSet.addTransition(ChangeTransform())
            .addTransition(ChangeBounds())
            .addTransition(Fade())
            .addTransition(Slide())

        TransitionManager.beginDelayedTransition(tv_text_change.parent as ViewGroup, transitionSet)
        if (tv_text_change.tag == 1) {
            tv_text_change.tag = 0
            tv_text_change.textSize = 14f
            tv_text_change.typeface = Typeface.DEFAULT
        } else {
            tv_text_change.tag = 1
            tv_text_change.textSize = 32f
            tv_text_change.typeface = Typeface.DEFAULT_BOLD
        }
    }

    override fun applyThemeResource() {
        super.applyThemeResource()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}