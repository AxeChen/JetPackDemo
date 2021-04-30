package com.axe.paging.simple

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 *
 */
class PagingViewModel {

    fun getArticleData2(): LiveData<PagingData<ArticleBean>> {
        return getArticleData().asLiveData()
    }

    private fun getArticleData(string: String = ""): Flow<PagingData<ArticleBean>> {
        return Pager(PagingConfig(20)) {
            PageKeyAdapterDataSource(string)
        }.flow
    }
}