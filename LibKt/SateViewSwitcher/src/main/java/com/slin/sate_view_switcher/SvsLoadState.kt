package com.slin.core.net.status



/**
 * author: slin
 * date: 2020/9/25
 * description:
 *
 */
/**
 * 加载状态
 * @param loadState 加载状态
 * @param failCause 加载失败原因
 * @param hasData 是否有加载到数据
 */
sealed class SvsState(
    val loadState: SvsLoadState,
    val failCause: Throwable? = null,
    val hasData: Boolean = false
) {

    object Initial : SvsState(SvsLoadState.Initial, null, false)

    class Loading(hasData: Boolean) : SvsState(SvsLoadState.Loading, null, hasData)

    class LoadSuccess(hasData: Boolean) : SvsState(SvsLoadState.Success, null, hasData)

    class LoadFail(throwable: Throwable) : SvsState(
        SvsLoadState.Fail,
        throwable,
        false
    )

}

/**
 * 加载状态
 * 初始化  ->  加载中   -> 加载成功
 *                     -> 加载失败
 */
enum class SvsLoadState {
    Initial,
    Loading,
    Success,
    Fail
}

