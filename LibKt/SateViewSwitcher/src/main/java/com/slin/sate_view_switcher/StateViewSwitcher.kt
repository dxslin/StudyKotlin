package com.slin.sate_view_switcher

import android.view.View
import android.view.ViewGroup
import com.slin.core.net.status.SvsState


/**
 * author: slin
 * date: 2020/9/25
 * description: 状态页切换
 *
 */

class StateViewSwitcher(
    private val contentView: View,
    private val clickReload: (SvsState) -> Unit = { },
) {

    companion object {

        private var factory: StateView.Factory = DefaultStateViewFactory()

        fun config(factory: StateView.Factory) {
            this.factory = factory
        }
    }

    private val stateView: StateView = factory.create()

    /**
     * contentView 的父布局
     */
    private val parent: ViewGroup

    /**
     * contentView的参数
     */
    private val contentViewParams: ViewGroup.LayoutParams

    /**
     * contentView在parent中的位置
     */
    private val contentViewIndex: Int

    /**
     * 上次添加的状态view，为空则没有加载
     */
    private var lastAddSateView: View? = null

    /**
     * 当前状态
     */
    private var curState: SvsState

    init {
        val contentViewParent = contentView.parent
        checkNotNull(contentViewParent) { "ContentView without parent" }
        check(contentViewParent is ViewGroup) { "ContentView parent is not a ViewGroup" }
        parent = contentViewParent
        contentViewParams = contentView.layoutParams
        contentViewIndex = parent.indexOfChild(contentView)
        lastAddSateView = contentView
        stateChange(SvsState.Initial)
        curState = SvsState.Initial
    }


    fun stateChange(state: SvsState) {
        if (curState == state) {
            return
        }
        curState = state
        val stateView = stateView.getStateView(parent, state)

        (stateView ?: contentView).let { view ->
            //如果创建的view与上次添加的view不一样且不为空，那么需要移除之前的，再添加
            //如果一样则不需要任何操作
            if (lastAddSateView != view) {
                lastAddSateView?.also {
                    parent.removeView(it)
                    lastAddSateView = null
                }
                addStateView(view)
            }
        }
        if (state is SvsState.LoadFail) {
            stateView?.apply {
                setOnClickListener { clickReload(state) }
            }
        }
    }

    private fun addStateView(view: View) {
        parent.addView(view, contentViewIndex, contentViewParams)
        lastAddSateView = view
    }

}

