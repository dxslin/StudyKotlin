package com.slin.core.net.status

import com.slin.core.net.Errors


/**
 * author: slin
 * date: 2020/9/25
 * description:
 *
 */
/**
 * 加载状态
 * @param dataLoadState 加载状态
 * @param failState 加载失败状态，为空则加载成功
 */
sealed class State(val dataLoadState: DataLoadState, val failState: DataFailState? = null) {

    object Initial : State(DataLoadState.Initial, null)

    object Loading : State(DataLoadState.Loading, null)

    object LoadSuccess : State(DataLoadState.Success, null)

    class LoadFail(throwable: Throwable) : State(
        DataLoadState.Fail,
        when (throwable) {
            is Errors.NoNetworkError -> DataFailState.NoNetwork
            else -> DataFailState.Error(throwable)
        }
    )

    object NoData : State(DataLoadState.Fail, DataFailState.NoData)

//    companion object {
//
//        fun initial(): State = State(LoadState.Initial, null)
//
//        fun loading(): State = State(LoadState.Loading, null)
//
//        fun loadSuccess(): State = State(LoadState.Success, null)
//
//        fun loadFail(throwable: Throwable): State = State(
//            LoadState.Fail,
//            when (throwable) {
//                is Errors.NoNetworkError -> FailState.NoNetwork
//                else -> FailState.Error(throwable)
//            }
//        )
//
//        fun loadFailNoData(): State = State(LoadState.Fail, FailState.NoData)
//
//    }

}

/**
 * 加载状态
 * 初始化  ->  加载中   -> 加载成功
 *                     -> 加载失败
 */
enum class DataLoadState {
    Initial,
    Loading,
    Success,
    Fail
}

sealed class DataFailState {
    /**
     * 无数据
     */
    object NoData : DataFailState()

    /**
     * 加载异常报错，可能是IO错误，也可能是程序异常
     */
    data class Error(val throwable: Throwable) : DataFailState()

    /**
     * 无网络
     */
    object NoNetwork : DataFailState()
}

