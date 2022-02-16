package com.slin.study.kotlin.ui.librarycase.matrix

import android.content.Context
import com.slin.core.logger.logd
import com.tencent.matrix.plugin.DefaultPluginListener
import com.tencent.matrix.plugin.Plugin
import com.tencent.matrix.report.Issue

/**
 * TestPluginListener
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/2/9
 */
class TestPluginListener(private val context: Context) : DefaultPluginListener(context) {

    private val TAG = TestPluginListener::class.java.simpleName

    override fun onInit(plugin: Plugin?) {
        super.onInit(plugin)
        logd { "onInit() called with: plugin = [$plugin]" }
    }

    override fun onStart(plugin: Plugin?) {
        super.onStart(plugin)
        logd { "onStart() called with: plugin = [$plugin]" }
    }

    override fun onStop(plugin: Plugin?) {
        super.onStop(plugin)
        logd { "onStop() called with: plugin = [$plugin]" }
    }

    override fun onDestroy(plugin: Plugin?) {
        super.onDestroy(plugin)
        logd { "onDestroy() called with: plugin = [$plugin]" }
    }

    override fun onReportIssue(issue: Issue?) {
        super.onReportIssue(issue)
        logd { "onReportIssue() called with: issue = [$issue]" }
    }
}