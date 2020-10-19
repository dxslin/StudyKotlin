package com.slin.git.weight.anim

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.slin.core.logger.logd


/**
 * author: slin
 * date: 2020/10/19
 * description:
 *
 */
interface AdapterItemAnim {

    private val listAnim: MutableList<ItemAnimator>
        get() = mutableListOf()

    private val listener: ItemAnimator.Listener
        get() = object : ItemAnimator.Listener {
            override fun onAnimatorStart(animator: ItemAnimator) {
                logd { "onAnimatorStart $animator " }
                listAnim.add(animator)
            }

            override fun onAnimatorEnd(animator: ItemAnimator) {
                logd { "onAnimatorEnd $animator " }
                listAnim.remove(animator)
            }

        }

    val animatorFactory: ItemAnimator.Factory
        get() = SimpleItemAnimator.Factory()


    fun attachViewAnim(holder: RecyclerView.ViewHolder) {
        val view = holder.itemView
        val itemAnimator = animatorFactory.create()
        itemAnimator.addAnimatorListener(listener)
        itemAnimator.animator(view)
    }

    fun detachViewAnim(holder: RecyclerView.ViewHolder) {
        listAnim.forEach {
            it.cancel()
        }
    }

}

interface ItemAnimator {

    fun animator(view: View)

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

internal val defaultAnimation = AlphaAnimation(0.3f, 1f).apply {
    interpolator = LinearInterpolator()
    duration = 300
}

open class SimpleItemAnimator(private val anim: Animation = defaultAnimation) : ItemAnimator {

    class Factory : ItemAnimator.Factory {
        override fun create(): ItemAnimator {
            return SimpleItemAnimator()
        }
    }

    private val animatorListeners = mutableListOf<ItemAnimator.Listener>()

    private var isStarted = false
    private var isEnded = false

    init {
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                dispatchStart()
            }

            override fun onAnimationEnd(animation: Animation) {
                dispatchEnd()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

    override fun animator(view: View) {
        view.startAnimation(anim)
    }

    override fun cancel() {
        if (!anim.hasEnded()) {
            anim.cancel()
        }
        dispatchEnd()
    }

    override fun addAnimatorListener(listener: ItemAnimator.Listener) {
        animatorListeners.add(listener)
    }

    override fun removeAnimatorListener(listener: ItemAnimator.Listener) {
        animatorListeners.remove(listener)
    }

    private fun dispatchEnd() {
        if (!isStarted || isEnded) {
            return
        }
        isEnded = true
        animatorListeners.forEach {
            it.onAnimatorEnd(this)
        }
        animatorListeners.clear()
    }

    private fun dispatchStart() {
        isStarted = true
        animatorListeners.forEach {
            it.onAnimatorStart(this)
        }
    }
}

