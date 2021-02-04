package com.axe.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    // 只有Retrofit的情况
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<Response<ArticleListBean>>

    @GET("article/list/{page}/json")
    suspend fun getArticleList2(@Path("page") page: Int): Response<ArticleListBean>

}