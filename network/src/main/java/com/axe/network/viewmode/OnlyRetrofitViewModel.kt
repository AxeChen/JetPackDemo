package com.axe.network.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axe.network.ArticleBean
import com.axe.network.ArticleListBean
import com.axe.network.Response
import com.axe.network.RetrofitManger
import retrofit2.Call
import retrofit2.Callback

class OnlyRetrofitViewModel : ViewModel() {

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
                    val res = response.body()
                    res?.run {
                        if (res.info == 0) {
                            articlesLiveData.postValue(res.data.datas)
                        } else {
                            articlesLiveData.postValue(mutableListOf())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Response<ArticleListBean>>, t: Throwable) {
                // 错误的情况
                t.printStackTrace()
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

