package com.axe.paging.simple

import androidx.paging.PagingSource

/**
 *  PagingSource来给Paging提供源数据
 */
class PageKeyAdapterDataSource(params:String) : PagingSource<Int, ArticleBean>() {

    private var params:String = ""

    init {
        this.params = params
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        val page = params.key ?: 0
        return LoadResult.Page(data = buildList(), nextKey = page + 1, prevKey = null)
    }

    private fun buildList(): MutableList<ArticleBean> {
        var articles = mutableListOf<ArticleBean>()
        for (i in 0 until 20) {
            articles.add(ArticleBean("分享一款Nginx 管理可视化神器", System.currentTimeMillis().toString()))
        }
        return articles
    }
}