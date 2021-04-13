package com.axe.network

import com.axe.network.response.Response
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    // 只有Retrofit的情况
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<Response<ArticleListBean>>

    // 和协程联用
    @GET("article/list/{page}/json")
    suspend fun getArticleList2(@Path("page") page: Int): Response<ArticleListBean>

    // 和Rxjava联用
    @GET("article/list/{page}/json")
    fun getArticleList3(@Path("page") page: Int): Observable<Response<ArticleListBean>>
}