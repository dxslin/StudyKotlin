package com.slin.core.utils

import android.app.Activity
import android.content.SharedPreferences
import com.slin.core.di.SingletonHolderSingleArg


/**
 * author: slin
 * date: 2020/9/10
 * description: 主题设置帮助类
 * 1. 初始化，设置主题id列表和主题名称
 * 2. 应用主题，在activity基础类里面
 * 3. 切换主题，保存主题id index，通知activity重建，重建动画
 */
data class ThemeItemData(val themeRes: Int, val name: String)

class ThemeHelper(private val sharedPreferences: SharedPreferences) {

    companion object : SingletonHolderSingleArg<ThemeHelper, SharedPreferences>(::ThemeHelper)

    private val themeDataList: List<ThemeItemData> = mutableListOf()

    private val activityList: List<Activity> = mutableListOf()

//    public fun applyTheme(context: Context) {
//        context.setTheme()
//    }

    public fun switchTheme(index: Int) {

    }


}