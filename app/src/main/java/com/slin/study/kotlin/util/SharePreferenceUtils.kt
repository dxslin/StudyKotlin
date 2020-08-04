package com.slin.study.kotlin.util

import android.content.Context
import android.content.SharedPreferences
import com.slin.study.kotlin.StudyKotlinApplication


/**
 * author: slin
 * date: 2020/8/4
 * description:
 *
 */

const val DEFAULT_SP_NAME = "sp_study_kotlin"

class SharePreferenceUtils {
    companion object {
        private val sharedPreferences: SharedPreferences by lazy {
            StudyKotlinApplication.INSTANCE.getSharedPreferences(
                DEFAULT_SP_NAME,
                Context.MODE_PRIVATE
            )
        }

        fun setTheme(index: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("theme", index)
            editor.apply()
        }

        fun getTheme(): Int {
            return sharedPreferences.getInt("theme", 0)
        }
    }
}

