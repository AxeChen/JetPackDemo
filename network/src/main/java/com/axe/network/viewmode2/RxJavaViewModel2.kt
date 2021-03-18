package com.axe.network.viewmode2

import androidx.lifecycle.MutableLiveData
import com.axe.network.ArticleBean
import com.axe.network.RetrofitManger
import com.axe.network.exception.CustomException
import com.axe.network.result.ResultResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RxJavaViewModel2 : BaseViewModel() {

    val api by lazy { RetrofitManger.getApiService() }
    var articlesLiveData: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getArticles(page: Int) {
        var disposable = api.getArticleList3(page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                executeResponseNotCoroutines(it).let {
                    when (it) {
                        is ResultResponse.Success -> {
                            articlesLiveData.postValue(it.data?.datas)
                        }
                        is ResultResponse.Error -> {
//                            showError(it.error!!)
                        }
                    }
                }
            }, {
                // 异常处理
                showError(CustomException.handleException(it).displayMessage!!)
            })
        mCompositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}