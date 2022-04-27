package com.slin.version.plugin

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.implementation

object Dependencies {
    object Slin {
        private const val slin_library_version = "1.1.3"

        const val score = "io.github.dxslin:Score:${slin_library_version}"
        const val viewBindingExt =
            "io.github.dxslin:ViewBindingExt:${slin_library_version}"
        const val dialog =
            "io.github.dxslin:SlinDialog:${slin_library_version}"
        const val view_pager_indicator =
            "io.github.dxslin:ViewPagerIndicator:${slin_library_version}"
        const val stateViewSwitcher =
            "io.github.dxslin:StateViewSwitcher:${slin_library_version}"
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

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/appcompat
         */
        private const val appcompatVersion = "1.4.1"

        /**
         * https://github.com/material-components/material-components-android/releases
         */
        private const val materialVersion = "1.5.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/constraintlayout
         */
        private const val constraintLayoutVersion = "2.1.3"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/vectordrawable
         */
        private const val vectorDrawableVersion = "1.1.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/navigation
         */
        private const val navigationVersion = "2.4.2"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/dynamicanimation
         */
        private const val dynamicAnimationVersion = "1.0.0"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout
         */
        private const val swipeRefreshLayoutVersion = "1.2.0-alpha01"

        /**
         * https://developer.android.google.cn/jetpack/androidx/releases/recyclerview
         */
        private const val recyclerViewVersion = "1.2.1"

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
        const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${okhttp_version}"
    }

    /**
     * https://github.com/square/retrofit
     */
    object Retrofit {
        private const val retrofit_version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:${retrofit_version}"
        const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${retrofit_version}"
    }

    object Logger {
        /**
         * https://github.com/JakeWharton/timber
         */
        private const val timber_version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:${timber_version}"
    }

    /**
     * https://github.com/bumptech/glide
     */
    object Glide {
        private const val glide_version = "4.13.1"
        const val glide = "com.github.bumptech.glide:glide:${glide_version}"
        const val glide_compiler = "com.github.bumptech.glide:compiler:${glide_version}"
    }

    //test
    object Test {
        private const val junit_version = "4.12"
        private const val junit_ext_version = "1.1.2"
        private const val espresso_core_version = "3.2.0"

        const val junit = "junit:junit:${junit_version}"
        const val junit_ext = "androidx.test.ext:junit:${junit_ext_version}"
        const val espresso_core =
            "androidx.test.espresso:espresso-core:${espresso_core_version}"

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

        const val matrix_gradle_plugin = "com.tencent.matrix:matrix-gradle-plugin:${matrix_version}"

        val matrix_android_lib = fullName("matrix-android-lib")
        val matrix_android_commons = fullName("matrix-android-commons")
        val matrix_trace_canary = fullName("matrix-trace-canary")
        val matrix_resource_canary_android = fullName("matrix-resource-canary-android")
        val matrix_resource_canary_common = fullName("matrix-resource-canary-common")
        val matrix_io_canary = fullName("matrix-io-canary")
        val matrix_sqlite_lint_android_sdk = fullName("matrix-sqlite-lint-android-sdk")
        val matrix_battery_canary = fullName("matrix-battery-canary")
        val matrix_hooks = fullName("matrix-hooks")

//        implementation Dependencies.Matrix.matrix_android_lib
//        implementation Dependencies.Matrix.matrix_android_commons
//        implementation Dependencies.Matrix.matrix_trace_canary
//        implementation Dependencies.Matrix.matrix_resource_canary_android
//        implementation Dependencies.Matrix.matrix_resource_canary_common
//        implementation Dependencies.Matrix.matrix_io_canary
//        implementation Dependencies.Matrix.matrix_sqlite_lint_android_sdk
//        implementation Dependencies.Matrix.matrix_battery_canary
//        implementation Dependencies.Matrix.matrix_hooks

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
        private const val longan_version = "1.0.3"
        const val longan = "com.github.DylanCaiCoding.Longan:longan:${longan_version}"
        const val longan_design = "com.github.DylanCaiCoding.Longan:longan-design:${longan_version}"

        /**
         * 悬浮窗
         * https://github.com/shenzhen2017/EasyFloat
         */
        const val easyfloat = "io.github.shenzhen2017:easyfloat:1.0.2"


    }


}