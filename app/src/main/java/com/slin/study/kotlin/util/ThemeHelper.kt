package com.slin.study.kotlin.util

import android.content.Context
import com.slin.study.kotlin.R


/**
 * author: slin
 * date: 2020/8/4
 * description:
 *
 */

val THEME_ARRAY = arrayOf("默认", "暗夜", "粉红", "魔力黑")

class ThemeHelper {
    companion object {
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

    }


}

