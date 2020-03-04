package com.jogger.http.service

import com.jogger.entity.ArticleData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {//Cookie: JSESSIONID=9CF61CDE1A602835A152B72B8967A2CE

    @POST("/yiyan/getfeeds")//"v","3.35"
    suspend fun getSubcribeArticles(
        @Query("v") v: String,
        @Query("ltid") feedid: String?
    ): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getAllStarTextCards(@Query("v") v: String): ArticleData

    @GET("/yiyan/getoriginaltextcard")
    suspend fun getOriginStarTextCards(@Query("v") v: String): ArticleData

    @GET("/yiyan/getalltextcard")
    suspend fun getStarTextCardsByType(
        @Query("v") v: String,
        @Query("category") category: Int
    ): ArticleData
}