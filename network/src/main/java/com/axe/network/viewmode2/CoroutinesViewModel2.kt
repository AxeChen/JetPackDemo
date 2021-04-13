package com.axe.network.viewmode2

import androidx.lifecycle.MutableLiveData
import com.axe.network.ArticleBean
import com.axe.network.RetrofitManger
import com.axe.network.exception.ApiException
import com.axe.network.response.Response
import com.axe.network.result.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutinesViewModel2 : BaseViewModel() {
    val api by lazy { RetrofitManger.getApiService() }
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun getArticles2(page: Int) {
        launch(block = {
            executeResponse(api.getArticleList2(page)).let {
                when (it) {
                    is ResultResponse.Success -> {
                        articlesLiveData.postValue(it.data.datas)
                    }
                    is ResultResponse.Error2 -> {
                        // 业务上面的错误
                        articlesLiveData.postValue(mutableListOf())
                        showError(it.apiException.displayMessage)
                    }
                }
            }
        }, exception = {
            // 系统的错误
            // 1、本地定义liveData发送
            showError(it.displayMessage)
            // 2、公共livedata处理
        })
    }

    /**
     * 与协程联用的数据解析方式
     */
    private suspend fun <T : Any> executeResponse(response: Response<T>): ResultResponse<T> {
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
}