package com.slin.study.kotlin.ui.jetpack.hlit

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier


/**
 * author: slin
 * date: 2020/11/20
 * description: 依赖注入Module
 *
 * 1. 使用binds绑定注入项，全部使用抽象方法和抽象类；provides注解只能在非抽象方法上使用
 * 2. 如果返回相同类型的话，那么可以使用`@Qualifier`标记的自定义注解限定符来区分，使用时需要加上限定符来区分
 * 3. InstallIn的参数可以为下面表格这些，它们面向不同的对象
 *  Hilt 组件	                注入器面向的对象
 *  ApplicationComponent	    Application
 *  ActivityRetainedComponent	ViewModel
 *  ActivityComponent	        Activity
 *  FragmentComponent	        Fragment
 *  ViewComponent	            View
 *  ViewWithFragmentComponent	带有 @WithFragmentBindings 注释的 View
 *  ServiceComponent	        Service
 *
 *
 *
 *
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class BindsModule {

    @Binds
    @AnalyticsServiceImplAnnotation
    abstract fun bindAnalyticsService(serviceImpl: AnalyticsServiceImpl): AnalyticsService

    @Binds
    @AnalyticsServiceOtherAnnotation
    abstract fun bindAnalyticsServiceOther(serviceImpl: AnalyticsServiceOther): AnalyticsService

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsServiceImplAnnotation

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsServiceOtherAnnotation
