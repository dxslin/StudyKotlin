object Versions {
    const val kotlin_version = "1.3.72"
    const val core_ktx_version = "1.3.1"

    const val appcompat_version = "1.2.0"

    const val kodein_version = "7.0.0"

    const val okhttp_version = "4.8.1"

    const val retrofit_version = "2.9.0"

    const val junit_version = "4.12"
    const val junit_ext_version = "1.1.2"
    const val espresso_core_version = "3.2.0"

}


object Dependencies {
    //kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"
    const val core_ktx = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin_version}"

    //android x
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"

    //kodein 依赖注入
    const val kodein_android_core =
        "org.kodein.di:kodein-di-framework-android-core:${Versions.kodein_version}"
    const val kodein_android_x =
        "org.kodein.di:kodein-di-framework-android-x:${Versions.kodein_version}"

    //okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"

    //test
    const val junit = "junit:junit:${Versions.junit_version}"
    const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext_version}"
    const val espresso_core =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core_version}"

}