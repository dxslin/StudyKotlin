package com.slin.study.kotlin.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.slin.study.kotlin.R


/**
 * author: slin
 * date: 2020/8/4
 * description:
 *
 */

val THEME_ARRAY = arrayOf("默认", "暗夜", "粉红", "魔力黑")
val THEME_NIGHT_MODE = arrayOf("跟随系统", "白天", "夜间", "跟随电量")

object ThemeHelper {

    private val activityList = arrayListOf<Activity>()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activity?.let {
                    activityList.remove(it)
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.let {
                    activityList.add(it)
                }
            }

        })
    }

    fun recreate() {
        for (i in activityList.size - 1 downTo 0) {
            activityList[i].recreate()
        }
    }

    fun applyTheme(context: Context) {
        val theme = when (getThemeIndex()) {
            0 -> R.style.AppTheme
            1 -> R.style.AppTheme_Dark
            2 -> R.style.AppTheme_Pink
            3 -> R.style.AppTheme_Dark2
            else -> R.style.AppTheme
        }
        context.setTheme(theme)
    }

    fun getThemeIndex(): Int {
        return SharePreferenceUtils.getTheme()
    }

    fun saveThemeIndex(index: Int) {
        SharePreferenceUtils.setTheme(index)
    }

    /**
     * 夜间模式
     */
    fun applyNightMode() {
        val mode = when (getNightModeIndex()) {
            0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            1 -> AppCompatDelegate.MODE_NIGHT_NO
            2 -> AppCompatDelegate.MODE_NIGHT_YES
            3 -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun getNightModeIndex(): Int {
        return SharePreferenceUtils.getNightMode()
    }

    fun saveNightModeIndex(index: Int) {
        SharePreferenceUtils.setNightMode(index)
    }


}

