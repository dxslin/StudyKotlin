package com.slin.git.ui.home.view


/**
 * author: slin
 * date: 2020/9/16
 * description:
 *
 */
data class HomeViewState(
    val isLoading: Boolean,
    val throwable: Throwable?
) {
    companion object {
        fun initial(): HomeViewState = HomeViewState(
            isLoading = false,
            throwable = null
        )
    }
}
