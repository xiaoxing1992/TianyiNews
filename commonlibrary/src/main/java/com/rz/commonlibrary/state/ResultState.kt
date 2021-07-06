package com.rz.commonlibrary.state

import androidx.lifecycle.MutableLiveData
import com.rz.commonlibrary.network.AppException
import com.rz.commonlibrary.network.BaseResponse
import com.rz.commonlibrary.network.ExceptionHandle

/**
 * @Author:         renzhengwei
 * @CreateDate:     2021/7/5 3:11 下午
 * @Description:
 */
sealed class ResultState<out T> {
    companion object{
        fun <T> onAppSuccess(data:T):ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage:String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
    }
    data class Loading(val loadingMessage:String) : ResultState<Nothing>()
    data class Success<out T>(val data:T):ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
}

/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>) {
    value = when {
        result.isSucces() -> {
            ResultState.onAppSuccess(result.getResponseData())
        }
        else -> {
            ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
        }
    }
}

/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = ResultState.onAppSuccess(result)
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    this.value = ResultState.onAppError(ExceptionHandle.handleException(e))
}
