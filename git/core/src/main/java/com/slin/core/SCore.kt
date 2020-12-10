package com.slin.core

//import com.slin.git.di.CoreComponent
import android.app.Application
import com.slin.core.di.CoreComponentDependencies
import com.slin.core.logger.initLogger
import com.slin.git.di.DaggerSCoreComponent
import com.slin.git.di.SCoreComponent
import dagger.hilt.android.EntryPointAccessors

/**
 * author: slin
 * date: 2020/9/2
 * description: 基础类，封装了一些常用的第三方库
 *
 */

/**
 * # SCore JetPack封装核心库
 *
 * Hilt + DataStore + okhttp + retrofit + gilde + timber
 *
 * 使用步骤：
 * ### 1. 添加Hilt生成插件
 * 根目录build.gradle添加：
 * ```groovy
 * classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
 * ```
 *
 * module目录build.gradle添加：
 * ```groovy
 * plugins {
 *      id 'kotlin-kapt'
 *      id 'dagger.hilt.android.plugin'
 * }
 * ```
 * ```groovy
 * kapt com.google.dagger:hilt-android-compiler:2.28-alpha
 * kapt androidx.hilt:hilt-compiler:1.0.0-alpha02
 * ```
 *
 * ### 2. 新建ConfigModule.kt通过依赖注入配置项目
 * ```kotlin
 * @Module
 * @InstallIn(ApplicationComponent::class)
 * object ConfigModule {
 *
 *      @Provides
 *      fun provideAppConfig(
 *              @ApplicationContext context: Context,
 *      ): CoreConfig {
 *              return CoreConfig(
 *                  application = context as Application,
 *                  baseUrl = DefaultConfig.BASE_URL,
 *                  httpLogLevel = DefaultConfig.HTTP_LOG_LEVEL,
 *                  ...
 *              )
 *      }
 *
 * }
 * ```
 *
 * ### 3. 在创建Application添加HiltAndroidApp(hilt要求)注解，在OnCreate里面初始化Score
 * ```kotlin
 * @HiltAndroidApp
 * class XXXApplication :Application() {
 *
 *      override fun onCreate() {
 *          super.onCreate()
 *
 *          SCore.init(this)
 *
 *      }
 * }
 *
 * ```
 *
 *
 */
object SCore {

    lateinit var application: Application

    lateinit var coreComponent: SCoreComponent


    fun init(application: Application) {
        this.application = application
        coreComponent = DaggerSCoreComponent.builder()
            .coreDependencies(
                EntryPointAccessors.fromApplication(application,
                    CoreComponentDependencies::class.java)
            )
            .context(application)
            .build()
        coreComponent.inject(application)
        initLogger(BuildConfig.DEBUG)
    }

}
