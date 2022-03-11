package com.slin.study.kotlin.ui.librarycase.matrix

import android.app.Application
import com.slin.study.kotlin.ui.splash.SplashActivity
import com.tencent.matrix.Matrix
import com.tencent.matrix.batterycanary.BatteryMonitorPlugin
import com.tencent.matrix.batterycanary.monitor.BatteryMonitorConfig
import com.tencent.matrix.batterycanary.monitor.feature.JiffiesMonitorFeature
import com.tencent.matrix.iocanary.IOCanaryPlugin
import com.tencent.matrix.iocanary.config.IOConfig
import com.tencent.matrix.resource.ResourcePlugin
import com.tencent.matrix.resource.config.ResourceConfig
import com.tencent.matrix.trace.TracePlugin
import com.tencent.matrix.trace.config.TraceConfig

/**
 * MatrixUtil
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/2/10
 */
object MatrixUtil {


    fun init(application: Application) {
        val dynamicConfigImplDemo = DynamicConfigImplDemo()
        val ioCanaryPlugin = IOCanaryPlugin(
            IOConfig.Builder()
                .dynamicConfig(dynamicConfigImplDemo).build()
        )

        val batteryMonitorPlugin = BatteryMonitorPlugin(
            BatteryMonitorConfig.Builder()
                .enable(JiffiesMonitorFeature::class.java)
                .build()
        )

        // 性能检测
        val tracePlugin = TracePlugin(
            TraceConfig.Builder()
                .dynamicConfig(dynamicConfigImplDemo)
                .enableFPS(true)
                .enableAnrTrace(true)
                .enableStartup(true)
                .enableTouchEventTrace(true)
                .enableAppMethodBeat(true)
                .splashActivities(SplashActivity::class.java.name)
                .build()
        )

        // 内存泄漏排查
        val resourcePlugin = ResourcePlugin(
            ResourceConfig.Builder()
                .dynamicConfig(dynamicConfigImplDemo)
                .setAutoDumpHprofMode(ResourceConfig.DumpMode.MANUAL_DUMP)
                .setDetectDebuger(true)
                .build()
        )

        val builder = Matrix.Builder(application)
            .plugin(ioCanaryPlugin)
            .plugin(batteryMonitorPlugin)
            .plugin(tracePlugin)
            .plugin(resourcePlugin)
            .pluginListener(TestPluginListener(application))

        Matrix.init(builder.build())
            .startAllPlugins()

    }


}