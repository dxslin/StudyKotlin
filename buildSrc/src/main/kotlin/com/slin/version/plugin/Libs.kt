package com.slin.version.plugin

import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.implementation

object Versions {


    const val androidSdk = 31
    const val androidBuildTools = "30.0.3"
    const val androidMinSdk = 24

    val javaVersion = JavaVersion.VERSION_1_8

    const val ktlint = "0.40.0"

}

object Libs {
    private const val agpVersion = "7.1.1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$agpVersion"

    object Slin {
        private const val slinLibraryVersion = "1.1.3"

        const val score = "io.github.dxslin:Score:${slinLibraryVersion}"
        const val scoreMvi = "io.github.dxslin:Score-mvi:${slinLibraryVersion}"
        const val viewBindingExt =
            "io.github.dxslin:ViewBindingExt:${slinLibraryVersion}"
        const val dialog =
            "io.github.dxslin:SlinDialog:${slinLibraryVersion}"
        const val viewPagerIndicator =
            "io.github.dxslin:ViewPagerIndicator:${slinLibraryVersion}"
        const val stateViewSwitcher =
            "io.github.dxslin:StateViewSwitcher:${slinLibraryVersion}"
    }

    object Kotlin {
        private const val kotlin_version = "1.6.21"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}"

        object Coroutines {
            private const val version = "1.6.1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    /**
     * androidx ui
     * https://developer.android.google.cn/jetpack/androidx/explorer
     */
    object AndroidX {

        private const val coreVersion = "1.7.0"
        private const val appcompatVersion = "1.4.1"
        private const val materialVersion = "1.5.0"
        private const val constraintLayoutVersion = "2.1.3"
        private const val vectorDrawableVersion = "1.1.0"
        private const val navigationVersion = "2.4.2"
        private const val dynamicAnimationVersion = "1.0.0"
        private const val swipeRefreshLayoutVersion = "1.2.0-alpha01"
        private const val recyclerViewVersion = "1.2.1"
        private const val cardViewVersion = "1.0.0"

        const val core = "androidx.core:core-ktx:${coreVersion}"
        const val appcompat = "androidx.appcompat:appcompat:${appcompatVersion}"
        const val material = "com.google.android.material:material:${materialVersion}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}"
        const val vectorDrawable =
            "androidx.vectordrawable:vectordrawable:${vectorDrawableVersion}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment:${navigationVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui:${navigationVersion}"
        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${navigationVersion}"
        const val navigationUiKtx =
            "androidx.navigation:navigation-ui-ktx:${navigationVersion}"
        const val navigationSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${navigationVersion}"
        const val dynamicAnimation =
            "androidx.dynamicanimation:dynamicanimation:${dynamicAnimationVersion}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${swipeRefreshLayoutVersion}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${recyclerViewVersion}"
        const val cardView =
            "androidx.cardview:cardview:${cardViewVersion}"
    }

    /**
     * Jetpack library
     * https://developer.android.google.cn/jetpack/androidx/explorer
     */
    object Jetpack {
        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
         */
        private const val lifecycleVersion = "2.2.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/room
         */
        private const val roomVersion = "2.2.5"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/paging
         */
        private const val pagingVersion = "3.0.0-alpha05"  //3.0.0-alpha05

        /**
         * https://dagger.dev/hilt/gradle-setup
         * https://developer.android.google.cn/jetpack/androidx/releases/hilt
         */
        private const val hiltVersion = "2.38.1"
        private const val hiltViewModelVersion = "1.0.0-alpha03"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/datastore
         */
        private const val dataStoreVersion = "1.0.0"

        /**
         * https://github.com/google/protobuf-gradle-plugin/
         */
        private const val protobufPluginVersion = "0.8.18"
        private const val protobufVersion = "3.10.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/startup
         */
        private const val startup_version = "1.1.1"

        const val lifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${lifecycleVersion}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime:${lifecycleVersion}"
        const val lifecycleLivedataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}"
        const val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}"

        const val room = "androidx.room:room-runtime:${roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${roomVersion}"
        const val roomTest = "androidx.room:room-testing:${roomVersion}"     //test

        const val pagingRuntime = "androidx.paging:paging-runtime:${pagingVersion}"
        const val pagingCommon = "androidx.paging:paging-common:${pagingVersion}"    //test

        const val hilt = "com.google.dagger:hilt-android:${hiltVersion}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${hiltVersion}"
        const val hiltViewModel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${hiltViewModelVersion}"
        const val hiltViewModelCompiler =
            "androidx.hilt:hilt-compiler:${hiltViewModelVersion}"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${hiltVersion}"

        const val dataStoreCore = "androidx.datastore:datastore-core:${dataStoreVersion}"
        const val dataStorePreferences =
            "androidx.datastore:datastore-preferences:${dataStoreVersion}"
        const val protobufPlugin =
            "com.google.protobuf:protobuf-gradle-plugin:${protobufPluginVersion}"
        const val protobufLite = "com.google.protobuf:protobuf-javalite:${protobufVersion}"

        const val startup = "androidx.startup:startup-runtime:${startup_version}"


    }

    object Helper {

        private const val recyclerViewAdapterHelperVersion = "2.9.50"

        const val recyclerViewAdapterHelper =
            "com.github.CymChad:BaseRecyclerViewAdapterHelper:${recyclerViewAdapterHelperVersion}"

    }

    /**
     * kodein 依赖注入
     * https://docs.kodein.org/kodein-di/7.10/framework/android.html
     */
    object Kodein {
        private const val kodein_version = "7.0.0"

        const val kodein_jvm = "org.kodein.di:kodein-di-jvm:${kodein_version}"
        const val kodein_android_core =
            "org.kodein.di:kodein-di-framework-android-core:${kodein_version}"
        const val kodein_android_x =
            "org.kodein.di:kodein-di-framework-android-x:${kodein_version}"
    }

    /**
     * https://github.com/square/okhttp
     */
    object OkHttp {
        private const val okhttp_version = "4.9.3"

        const val okhttp = "com.squareup.okhttp3:okhttp:${okhttp_version}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${okhttp_version}"
    }

    /**
     * https://github.com/square/retrofit
     */
    object Retrofit {
        private const val retrofit_version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:${retrofit_version}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${retrofit_version}"
    }

    object Logger {
        /**
         * https://github.com/JakeWharton/timber
         */
        private const val timberVersion = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:${timberVersion}"
    }

    /**
     * https://github.com/bumptech/glide
     */
    object Glide {
        private const val glideVersion = "4.13.1"
        const val glide = "com.github.bumptech.glide:glide:${glideVersion}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${glideVersion}"
    }

    //test
    object Test {
        private const val junitVersion = "4.12"
        private const val junitExtVersion = "1.1.2"
        private const val espressoCoreVersion = "3.2.0"

        const val junit = "junit:junit:${junitVersion}"
        const val junitExt = "androidx.test.ext:junit:${junitExtVersion}"
        const val espressoCore =
            "androidx.test.espresso:espresso-core:${espressoCoreVersion}"

    }

    /**
     * https://github.com/Tencent/matrix
     */
    object Matrix {
        private const val matrix_version = "2.0.5"

        @JvmStatic
        private fun fullName(name: String): String {
            return "com.tencent.matrix:$name:$matrix_version"
        }

        const val matrixGradlePlugin = "com.tencent.matrix:matrix-gradle-plugin:${matrix_version}"

        val matrixAndroidLib = fullName("matrix-android-lib")
        val matrixAndroidCommons = fullName("matrix-android-commons")
        val matrixTraceCanary = fullName("matrix-trace-canary")
        val matrixResourceCanaryAndroid = fullName("matrix-resource-canary-android")
        val matrixResourceCanaryCommon = fullName("matrix-resource-canary-common")
        val matrixIoCanary = fullName("matrix-io-canary")
        val matrixSqliteLintAndroidSdk = fullName("matrix-sqlite-lint-android-sdk")
        val matrixBatteryCanary = fullName("matrix-battery-canary")
        val matrixHooks = fullName("matrix-hooks")

//        implementation Dependencies.Matrix.matrixAndroidLib
//        implementation Dependencies.Matrix.matrixAndroidCommons
//        implementation Dependencies.Matrix.matrixTraceCanary
//        implementation Dependencies.Matrix.matrixResourceCanaryAndroid
//        implementation Dependencies.Matrix.matrixResourceCanaryCommon
//        implementation Dependencies.Matrix.matrixIoCanary
//        implementation Dependencies.Matrix.matrixSqliteLintAndroidSdk
//        implementation Dependencies.Matrix.matrixBatteryCanary
//        implementation Dependencies.Matrix.matrixHooks

        @JvmStatic
        fun implementationMatrix(dependencyHandler: DependencyHandler) {
            val matrixModules = arrayOf(
                "matrix-commons",
                "matrix-battery-canary",
                "matrix-android-lib",
                "matrix-android-commons",
                "matrix-trace-canary",
                "matrix-resource-canary-android",
                "matrix-resource-canary-common",
                "matrix-io-canary",
                "matrix-sqlite-lint-android-sdk"
            )
            matrixModules.forEach { name ->
                dependencyHandler.implementation(
                    group = "com.tencent.matrix",
                    name = name,
                    version = matrix_version
                ) {
                    isChanging = true
                }
            }
        }

    }

    object Other {

        /**
         * 一些Kotlin可以使用的工具方法
         * https://github.com/DylanCaiCoding/Longan
         */
        private const val longanVersion = "1.0.3"
        const val longan = "com.github.DylanCaiCoding.Longan:longan:${longanVersion}"
        const val longanDesign = "com.github.DylanCaiCoding.Longan:longan-design:${longanVersion}"

        /**
         * 悬浮窗
         * https://github.com/shenzhen2017/EasyFloat
         */
        const val easyfloat = "io.github.shenzhen2017:easyfloat:1.0.2"


    }


}