package com.slin.study.kotlin.ui.transition

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.appbar.AppBarLayout
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_transition_detail.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_detail)

        //沉浸式
        window.statusBarColor = Color.TRANSPARENT
        //Toolbar
        setSupportActionBar(toolbar)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val transitionData: TransitionData? = intent?.extras?.getParcelable(INTENT_TRANSITION_DATA)
        transitionData?.let {
            tv_pageName.text = transitionData.title
            iv_pageIcon.setImageResource(transitionData.imgRes)
            tv_pageSubName.text = transitionData.abstract
            tv_pageDetail.text = transitionData.detail
        }


        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val percent = (verticalOffset.toFloat() * appBarLayout.totalScrollRange).absoluteValue
            toolbar.title = if (percent > 0.95) {
                transitionData?.title
            } else {
                ""
            }

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