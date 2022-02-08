package com.slin.version.plugin

object Dependencies {
    object Slin {
        private const val slin_library_version = "1.0.0"

        const val score = "com.github.dxslin.SlinLibrary:Score:${slin_library_version}"
        const val view_binding_ext =
            "com.github.dxslin.SlinLibrary:ViewBindingExt:${slin_library_version}"
        const val slin_dialog =
            "com.github.dxslin.SlinLibrary:SlinDialog:${slin_library_version}"
        const val view_pager_indicator =
            "com.github.dxslin.SlinLibrary:ViewPagerIndicator:${slin_library_version}"
    }

    object Kotlin {
        private const val kotlin_version = "1.6.0"
        const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}"
        const val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5"
        const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}"
        const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}"
    }

    //android x ui
    object AndroidX {
        private const val appcompat_version = "1.2.0"
        private const val material_version = "1.2.0"
        private const val constraint_layout_version = "2.0.1"
        private const val vector_drawable_version = "1.1.0"
        private const val navigation_version = "2.2.0"
        private const val dynamic_animation_version = "1.0.0"
        private const val swipe_refresh_layout_version = "1.2.0-alpha01"
        private const val recycler_view_version = "1.1.0"

        const val appcompat = "androidx.appcompat:appcompat:${appcompat_version}"
        const val material = "com.google.android.material:material:${material_version}"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${constraint_layout_version}"
        const val vector_drawable =
            "androidx.vectordrawable:vectordrawable:${vector_drawable_version}"
        const val navigation_fragment =
            "androidx.navigation:navigation-fragment:${navigation_version}"
        const val navigation_ui = "androidx.navigation:navigation-ui:${navigation_version}"
        const val navigation_fragment_ktx =
            "androidx.navigation:navigation-fragment-ktx:${navigation_version}"
        const val navigation_ui_ktx =
            "androidx.navigation:navigation-ui-ktx:${navigation_version}"
        const val navigation_safe_args =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${navigation_version}"
        const val dynamic_animation =
            "androidx.dynamicanimation:dynamicanimation:${dynamic_animation_version}"
        const val swipe_refresh_layout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${swipe_refresh_layout_version}"
        const val recycler_view =
            "androidx.recyclerview:recyclerview:${recycler_view_version}"
    }

    //jetpack library
    object Jetpack {
        private const val lifecycle_version = "2.2.0"
        private const val room_version = "2.2.5"
        private const val paging_version = "3.0.0-alpha05"  //3.0.0-alpha05

        private const val hilt_version = "2.38.1"
        private const val hilt_viewmodel_version = "1.0.0-alpha03"

        private const val data_store_version = "1.0.0-alpha02"
        private const val protobuf_plugin_version = "0.8.13"
        private const val protobuf_version = "3.10.0"


        const val lifecycle_extensions =
            "androidx.lifecycle:lifecycle-extensions:${lifecycle_version}"
        const val lifecycle_runtime =
            "androidx.lifecycle:lifecycle-runtime:${lifecycle_version}"

        const val room = "androidx.room:room-runtime:${room_version}"
        const val room_compiler = "androidx.room:room-compiler:${room_version}"
        const val room_ktx = "androidx.room:room-ktx:${room_version}"
        const val room_test = "androidx.room:room-testing:${room_version}"     //test

        const val paging_runtime = "androidx.paging:paging-runtime:${paging_version}"
        const val paging_common = "androidx.paging:paging-common:${paging_version}"    //test

        const val hilt = "com.google.dagger:hilt-android:${hilt_version}"
        const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${hilt_version}"
        const val hilt_viewmodel =
            "androidx.hilt:hilt-lifecycle-viewmodel:${hilt_viewmodel_version}"
        const val hilt_viewmodel_compiler =
            "androidx.hilt:hilt-compiler:${hilt_viewmodel_version}"
        const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${hilt_version}"

        const val data_store_core = "androidx.datastore:datastore-core:${data_store_version}"
        const val data_store_preferences =
            "androidx.datastore:datastore-preferences:${data_store_version}"
        const val protobuf_plugin =
            "com.google.protobuf:protobuf-gradle-plugin:${protobuf_plugin_version}"
        const val protobuf_lite = "com.google.protobuf:protobuf-javalite:${protobuf_version}"
    }

    //kodein 依赖注入
    object Kodein {
        private const val kodein_version = "7.0.0"

        const val kodein_jvm = "org.kodein.di:kodein-di-jvm:${kodein_version}"
        const val kodein_android_core =
            "org.kodein.di:kodein-di-framework-android-core:${kodein_version}"
        const val kodein_android_x =
            "org.kodein.di:kodein-di-framework-android-x:${kodein_version}"
    }

    object OkHttp {
        private const val okhttp_version = "4.8.1"

        const val okhttp = "com.squareup.okhttp3:okhttp:${okhttp_version}"
        const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${okhttp_version}"
    }

    //retrofit
    object Retrofit {
        private const val retrofit_version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:${retrofit_version}"
        const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${retrofit_version}"
    }

    //logger
    object Logger {
        private const val timber_version = "4.7.1"
        const val timber = "com.jakewharton.timber:timber:${timber_version}"
    }

    //glide
    object Glide {
        private const val glide_version = "4.11.0"
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

}