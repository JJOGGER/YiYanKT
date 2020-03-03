package com.jogger.http.service

import com.jogger.entity.ArticleData
import retrofit2.http.POST

interface ApiService {//Cookie: JSESSIONID=9CF61CDE1A602835A152B72B8967A2CE

    @POST("/yiyan/getfeeds")
    suspend fun  getSubcribeArticles(): ArticleData
}