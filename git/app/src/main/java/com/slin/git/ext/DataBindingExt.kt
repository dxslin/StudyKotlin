package com.slin.git.ext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.elevation.ElevationOverlayProvider
import com.slin.core.SCore
import com.slin.core.image.ImageLoader
import com.slin.core.image.load


/**
 * author: slin
 * date: 2020/9/17
 * description:  自定义的 DataBinding 适配器
 *
 */


@BindingAdapter("srcUrl", "placeholder", "centerCrop", "isCircle", requireAll = false)
fun ImageView.bindSrcUrl(
    srcUrl: String,
    placeholder: Drawable?,
    centerCrop: Boolean = true,
    isCircle: Boolean = false
) {
    val imageLoader: ImageLoader = SCore.coreComponent.imageLoader()
    imageLoader.load(
        this,
        placeholder = placeholder,
        url = srcUrl,
        isCenterCrop = centerCrop,
        isCircle = isCircle
    )

}

@BindingAdapter("elevationOverlay", requireAll = true)
fun View.bindElevationOverlay(preElevation: Float, elevation: Float) {
    if (preElevation == elevation) return
    val color = ElevationOverlayProvider(context)
        .compositeOverlayWithThemeSurfaceColorIfNeeded(elevation)
    setBackgroundColor(color)
}

@BindingAdapter("refreshColor", requireAll = true)
fun SwipeRefreshLayout.bindColor(previousColor: ColorDrawable, color: ColorDrawable) {
    if (previousColor == color) return
    setColorSchemeColors(color.color)
}


@BindingAdapter("fullscreen")
fun View.bindLayoutFullscreen(fullscreen: Boolean) {
    if (fullscreen) {
        ViewCompat.getWindowInsetsController(this)
            ?.hide(WindowInsetsCompat.Type.systemBars())
        ViewCompat.getWindowInsetsController(this)
            ?.hide(WindowInsetsCompat.Type.navigationBars())
    } else {
        ViewCompat.getWindowInsetsController(this)
            ?.show(WindowInsetsCompat.Type.systemBars())
        ViewCompat.getWindowInsetsController(this)
            ?.show(WindowInsetsCompat.Type.navigationBars())
    }
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsPadding(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, padding, _ ->
        val inset = WindowInsetsCompat.toWindowInsetsCompat(insets)
            .getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) inset.left else 0
        val top = if (applyTop) inset.top else 0
        val right = if (applyRight) inset.right else 0
        val bottom = if (applyBottom) inset.bottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )
    }
}

@BindingAdapter(
    "marginLeftSystemWindowInsets",
    "marginTopSystemWindowInsets",
    "marginRightSystemWindowInsets",
    "marginBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsMargin(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }

    doOnApplyWindowInsets { view, insets, _, margin ->
        val inset = WindowInsetsCompat.toWindowInsetsCompat(insets)
            .getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) inset.left else 0
        val top = if (applyTop) inset.top else 0
        val right = if (applyRight) inset.right else 0
        val bottom = if (applyBottom) inset.bottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = margin.left + left
            topMargin = margin.top + top
            rightMargin = margin.right + right
            bottomMargin = margin.bottom + bottom
        }
    }
}

fun View.doOnApplyWindowInsets(
    block: (View, WindowInsets, InitialPadding, InitialMargin) -> Unit
) {
    // Create a snapshot of the view's padding & margin states
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    // Set an actual OnApplyWindowInsetsListener which proxies to the given
    // lambda, also passing in the original padding & margin states
    setOnApplyWindowInsetsListener { v, insets ->
        block(v, insets, initialPadding, initialMargin)
        // Always return the insets, so that children can also use them
        insets
    }
    // request some insets
    requestApplyInsetsWhenAttached()
}

class InitialPadding(val left: Int, val top: Int, val right: Int, val bottom: Int)

class InitialMargin(val left: Int, val top: Int, val right: Int, val bottom: Int)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

private fun recordInitialMarginForView(view: View): InitialMargin {
    val lp = view.layoutParams as? ViewGroup.MarginLayoutParams
        ?: throw IllegalArgumentException("Invalid view layout params")
    return InitialMargin(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        // We're already attached, just request as normal
        requestApplyInsets()
    } else {
        // We're not attached to the hierarchy, add a listener to
        // request when we are
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

