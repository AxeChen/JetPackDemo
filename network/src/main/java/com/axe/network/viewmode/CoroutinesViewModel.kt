package com.axe.network.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axe.network.ArticleBean
import com.axe.network.RetrofitManger
import kotlinx.coroutines.launch

class CoroutinesViewModel : ViewModel() {

    val api by lazy { RetrofitManger.getApiService() }
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun getArticles(page: Int) {
        viewModelScope.launch {
            val respose = api.getArticleList2(page)
            if (respose.info == 0) {
                articlesLiveData.postValue(respose.data.datas)
            } else {
                articlesLiveData.postValue(mutableListOf())
            }

        }
    }

}