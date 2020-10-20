package com.slin.git.weight.anim.animator

import android.view.View


/**
 * author: slin
 * date: 2020/10/20
 * description:
 *
 */

interface ItemAnimator {

    fun animate(view: View)

    fun cancel()

    fun addAnimatorListener(listener: Listener)

    fun removeAnimatorListener(listener: Listener)

    interface Listener {

        fun onAnimatorStart(animator: ItemAnimator)

        fun onAnimatorEnd(animator: ItemAnimator)
    }

    interface Factory {
        fun create(): ItemAnimator
    }

}

