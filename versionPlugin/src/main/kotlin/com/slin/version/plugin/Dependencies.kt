package com.slin.version.plugin

object Versions {
    const val kotlin_version = "1.4.20"

    const val appcompat_version = "1.2.0"
    const val material_version = "1.2.0"
    const val constraint_layout_version = "2.0.1"
    const val vector_drawable_version = "1.1.0"
    const val navigation_version = "2.2.0"
    const val dynamic_animation_version = "1.0.0"
    const val swipe_refresh_layout_version = "1.2.0-alpha01"
    const val recycler_view_version = "1.1.0"

    const val lifecycle_version = "2.2.0"

    const val room_version = "2.2.5"

    const val paging_version = "3.0.0-alpha05"  //3.0.0-alpha05


    const val kodein_version = "7.0.0"

    const val okhttp_version = "4.8.1"

    const val retrofit_version = "2.9.0"

    const val timber_version = "4.7.1"

    const val glide_version = "4.11.0"

    const val hilt_version = "2.28-alpha"
    const val hilt_viewmodel_version = "1.0.0-alpha02"

    const val data_store_version = "1.0.0-alpha02"
    const val protobuf_plugin_version = "0.8.13"
    const val protobuf_version = "3.10.0"


    const val junit_version = "4.12"
    const val junit_ext_version = "1.1.2"
    const val espresso_core_version = "3.2.0"

}


object Dependencies {
    //kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val kotlin_coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_version}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"

    //android x ui
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val material = "com.google.android.material:material:${Versions.material_version}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val vector_drawable =
        "androidx.vectordrawable:vectordrawable:${Versions.vector_drawable_version}"
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment:${Versions.navigation_version}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation_version}"
    const val navigation_fragment_ktx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_version}"
    const val navigation_ui_ktx =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation_version}"
    const val navigation_safe_args =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation_version}"
    const val dynamic_animation =
        "androidx.dynamicanimation:dynamicanimation:${Versions.dynamic_animation_version}"
    const val swipe_refresh_layout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout_version}"
    const val recycler_view =
        "androidx.recyclerview:recyclerview:${Versions.recycler_view_version}"


    //jetpack library
    const val lifecycle_extensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_version}"
    const val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle_version}"

    const val room = "androidx.room:room-runtime:${Versions.room_version}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"
    const val room_test = "androidx.room:room-testing:${Versions.room_version}"     //test

    const val paging_runtime = "androidx.paging:paging-runtime:${Versions.paging_version}"
    const val paging_common = "androidx.paging:paging-common:${Versions.paging_version}"    //test

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"
    const val hilt_viewmodel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_viewmodel_version}"
    const val hilt_viewmodel_compiler =
        "androidx.hilt:hilt-compiler:${Versions.hilt_viewmodel_version}"
    const val hilt_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"

    const val data_store_core = "androidx.datastore:datastore-core:${Versions.data_store_version}"
    const val data_store_preferences =
        "androidx.datastore:datastore-preferences:${Versions.data_store_version}"
    const val protobuf_plugin =
        "com.google.protobuf:protobuf-gradle-plugin:${Versions.protobuf_plugin_version}"
    const val protobuf_lite = "com.google.protobuf:protobuf-javalite:${Versions.protobuf_version}"

    //kodein 依赖注入
    const val kodein_jvm = "org.kodein.di:kodein-di-jvm:${Versions.kodein_version}"
    const val kodein_android_core =
        "org.kodein.di:kodein-di-framework-android-core:${Versions.kodein_version}"
    const val kodein_android_x =
        "org.kodein.di:kodein-di-framework-android-x:${Versions.kodein_version}"

    //okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"

    //logger
    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"


    //test
    const val junit = "junit:junit:${Versions.junit_version}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext_version}"
    const val espresso_core =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core_version}"

}