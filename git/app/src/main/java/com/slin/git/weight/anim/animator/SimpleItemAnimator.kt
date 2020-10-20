package com.slin.git.weight.anim.animator

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.slin.core.logger.logd


/**
 * author: slin
 * date: 2020/10/20
 * description:
 *
 */

interface AnimationFactory {
    fun create(): Animation
}

open class SimpleItemAnimator @JvmOverloads constructor(
    animationFactory: AnimationFactory = object : AnimationFactory {
        override fun create(): Animation {
            return AlphaAnimation(0.3f, 1f).apply {
                interpolator = LinearInterpolator()
                duration = 300
            }
        }
    }
) : ItemAnimator {

    class Factory : ItemAnimator.Factory {
        override fun create(): ItemAnimator {
            return SimpleItemAnimator()
        }
    }

    private val animatorListeners = mutableListOf<ItemAnimator.Listener>()

    private var isStarted = false
    private var isEnded = false

    private val anim: Animation = animationFactory.create()

    init {
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                dispatchStart()
            }

            override fun onAnimationEnd(animation: Animation) {

                logd { "onAnimationEnd: $isEnded ${this@SimpleItemAnimator}" }
                dispatchEnd()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

    }

    override fun animate(view: View) {
        view.startAnimation(anim)
    }

    override fun cancel() {
        if (!anim.hasEnded()) {
            anim.cancel()
        }
        logd { "cancel: $isEnded $this" }
        dispatchEnd()
    }

    override fun addAnimatorListener(listener: ItemAnimator.Listener) {
        animatorListeners.add(listener)
    }

    override fun removeAnimatorListener(listener: ItemAnimator.Listener) {
        animatorListeners.remove(listener)
    }

    private fun dispatchEnd() {
        logd { "dispatchEnd: $isEnded $this" }
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
        if (isEnded) {
            throw IllegalStateException("Animator is end")
        }
        isStarted = true
        animatorListeners.forEach {
            it.onAnimatorStart(this)
        }
        anim.setAnimationListener(null)
    }
}


