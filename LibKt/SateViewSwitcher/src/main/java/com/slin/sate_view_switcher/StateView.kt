package com.slin.sate_view_switcher

import android.view.View
import android.view.ViewGroup
import com.slin.core.net.status.SvsState


/**
 * author: slin
 * date: 2020/9/29
 * description:
 *
 */


interface StateView {
    /**
     * 根据状态返回对应的View，如果null则使用正常的布局
     * @param parent 内容布局的父布局
     * @param state 加载状态
     */
    fun getStateView(parent: ViewGroup, state: SvsState): View?

    interface Factory {
        fun create(): StateView
    }

}
