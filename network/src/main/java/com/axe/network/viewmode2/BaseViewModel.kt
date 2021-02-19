package com.axe.network.viewmode2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axe.network.Response
import com.axe.network.ResultResponse
import com.axe.network.exception.ApiException
import com.axe.network.exception.CustomException
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    fun launch2(block: suspend CoroutineScope.() -> Unit, exception: (ApiException) -> Unit = {}) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            // 异常处理回调
            exception(CustomException.handleException(exception))
        }
        viewModelScope.launch(exceptionHandler) {
            // 请求数据
            block()
        }
    }

    suspend fun <T : Any> executeResponse(response: Response<T>): ResultResponse<T> {
        return withContext(Dispatchers.IO) {
            var code = response.info
            // 判断服务器返回的状态码
            when (code) {
                0 -> {
                    // 成功的情况
                    response.data?.let {
                        ResultResponse.Success(it)
                    } ?: ResultResponse.Error(code, response.msg)
                }
                2 -> {
                    // 失败的情况1，具体和业务相关
                    ResultResponse.Error(code, response.msg)
                    // 可以统一封装方式
                    ResultResponse.Error2(ApiException(code, response.msg))
                }
                3 -> {
                    // 失败的情况2，具体和业务相关
                    ResultResponse.Error(code, response.msg)
                }
                else -> {
                    // 失败的情况 未知
                    ResultResponse.Error(code, response.msg)
                }
            }
        }
    }
}