package com.axe.network.viewmode2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axe.network.response.Response
import com.axe.network.result.ResultResponse
import com.axe.network.exception.ApiException
import com.axe.network.exception.CustomException
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    // 异常LiveData
    var apiError: MutableLiveData<ApiException> = MutableLiveData()

    /**
     * 封装的展示异常信息的方法
     */
    open fun showError(error: String) {
        apiError.postValue(ApiException(CustomException.ERROR_TEXT, error))
    }

    fun launch(block: suspend CoroutineScope.() -> Unit, exception: (ApiException) -> Unit = {}) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            // 异常处理回调
            val apiException = CustomException.handleException(exception)
            apiError.postValue(apiException)
            exception(apiException)
        }
        viewModelScope.launch(exceptionHandler) {
            // 请求数据
            block()
        }
    }

    /**
     * 没有协程的情况数据解析方式
     */
    fun <T : Any> executeResponseNotCoroutines(response: Response<T>): ResultResponse<T> {
        var code = response.info
        // 判断服务器返回的状态码
        return when (code) {
            0 -> {
                if (response.data != null) {
                    ResultResponse.Success(response.data)
                } else {
                    val exception = ApiException(code, response.msg)
                    apiError.postValue(exception)
                    ResultResponse.Error2(ApiException(code, response.msg))
                }
            }
            else -> {
                var exception = ApiException(code, response.msg)
                apiError.postValue(exception)
                ResultResponse.Error2(exception)
            }
        }
    }
}