package com.axe.network.viewmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axe.network.ArticleBean
import com.axe.network.RetrofitManger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RxJavaViewModel : ViewModel() {
    val api by lazy { RetrofitManger.getApiService() }
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getArticles(page: Int) {
        var disposable = api.getArticleList3(page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.info == 0) {
                    articlesLiveData.postValue(it.data?.datas)
                } else {
                    articlesLiveData.postValue(mutableListOf())
                }
            }, {
                // 异常处理
            })
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}