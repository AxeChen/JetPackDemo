package com.axe.network.viewmode2

import androidx.lifecycle.MutableLiveData
import com.axe.network.ArticleBean
import com.axe.network.ArticleListBean
import com.axe.network.RetrofitManger
import com.axe.network.exception.CustomException
import com.axe.network.response.Response
import com.axe.network.result.ResultResponse
import retrofit2.Call
import retrofit2.Callback

class OnlyRetrofitViewModel2 : BaseViewModel() {

    val api by lazy { RetrofitManger.getApiService() }
    var calls: MutableList<Call<*>> = mutableListOf()
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun getArticles(page: Int) {
        val call = api.getArticleList(page)
        call.enqueue(object : Callback<Response<ArticleListBean>> {
            override fun onResponse(
                call: Call<Response<ArticleListBean>>,
                response: retrofit2.Response<Response<ArticleListBean>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        executeResponseNotCoroutines(it).let {
                            when (it) {
                                is ResultResponse.Success -> {
                                    articlesLiveData.postValue(it.data?.datas)
                                }
                                is ResultResponse.Error2 -> {
                                    showError(it.apiException.displayMessage)
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Response<ArticleListBean>>, t: Throwable) {
                // 错误的情况
                t.printStackTrace()
                showError(CustomException.handleException(t).displayMessage)
            }
        })
        calls.add(call)
    }

    override fun onCleared() {
        super.onCleared()
        calls.forEach {
            if (!it.isCanceled) {
                it.cancel()
            }
        }
    }
}