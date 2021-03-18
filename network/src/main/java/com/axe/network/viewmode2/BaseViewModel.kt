package com.axe.network.viewmode2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axe.network.response.Response
import com.axe.network.result.ResultResponse
import com.axe.network.exception.ApiException
import com.axe.network.exception.CustomException
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    // 异常LiveData
    var apiError: MutableLiveData<ApiException> = MutableLiveData()

    fun launch2(block: suspend CoroutineScope.() -> Unit, exception: (ApiException) -> Unit = {}) {
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

    open fun showError(error: String) {
        apiError.postValue(ApiException(CustomException.ERROR_TEXT, error))
    }

    suspend fun <T : Any> executeResponse(response: Response<T>): ResultResponse<T> {
        return withContext(Dispatchers.IO) {
            var code = response.info
            // 判断服务器返回的状态码
            when (code) {
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

    /**
     * 没有协程的情况
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