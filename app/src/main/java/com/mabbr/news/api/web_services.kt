package com.mabbr.news.api

import com.mabbr.news.NewsResponse
import com.mabbr.news.model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface web_services {

    @GET("v2/top-headlines/sources")
    fun getSources(

        @Query("apiKey") Key: String,
        @Query("category") category: String
    ): Call<SourceResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("sources") sources: String,

        ): Call<NewsResponse>

}