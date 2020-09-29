package com.slin.core.net

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.slin.core.R
import com.slin.sate_view_switcher.DefaultStateView
import com.slin.sate_view_switcher.StateView


/**
 * author: slin
 * date: 2020/9/29
 * description:
 *
 */

class CoreStateViewFactory : StateView.Factory {
    override fun create(): StateView {
        return CoreStateView()
    }
}

class CoreStateView : DefaultStateView() {

    private fun isNoNetwork(throwable: Throwable?): Boolean {
        return throwable is Errors.NoNetworkError
    }

    override fun obtainFailDrawable(context: Context, throwable: Throwable?): Drawable? {
        return if (isNoNetwork(throwable)) {
            ContextCompat.getDrawable(context, R.drawable.svs_ic_no_network)
        } else {
            ContextCompat.getDrawable(context, R.drawable.svs_ic_load_fail)
        }
    }

    override fun obtainFailText(context: Context, throwable: Throwable?): CharSequence {
        return if (isNoNetwork(throwable)) {
            context.getString(R.string.core_svs_no_network)
        } else {
            context.getString(R.string.core_svs_error)
        }
    }

}