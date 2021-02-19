package com.axe.network.viewmode2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.axe.network.ArticleBean
import com.axe.network.ResultResponse
import com.axe.network.RetrofitManger
import com.axe.network.exception.ApiException

class CoroutinesViewModel2 : BaseViewModel() {
    val api by lazy { RetrofitManger.getApiService() }
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    var apiError: MutableLiveData<ApiException> = MutableLiveData()

    fun getArticles(page: Int) {
        launch2(block = {
            val respose = api.getArticleList2(page)
            if (respose.info == 0) {
                articlesLiveData.postValue(respose.data?.datas)
            } else {
                // info 为其他值的时候
                articlesLiveData.postValue(mutableListOf())
            }
        }, exception = {
            // 系统的错误
            // 1、本地定义liveData发送
            apiError.postValue(it)
            // 2、公共livedata处理

        })
    }

    fun getArticles2(page: Int) {
        launch2(block = {
            executeResponse(api.getArticleList2(page)).let {
                when(it){
                    is ResultResponse.Success->{
                        articlesLiveData.postValue(it.data.datas)
                    }
                    is ResultResponse.Error2->{
                        Log.i("getArticles2",it.apiException.displayMessage)
                        articlesLiveData.postValue(mutableListOf())
                    }
                }
            }

        }, exception = {
            // 系统的错误
            // 1、本地定义liveData发送
            apiError.postValue(it)
            // 2、公共livedata处理

        })
    }
}