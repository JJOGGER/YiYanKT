package com.jogger.http.datasource

import com.jogger.http.basic.RetrofitManager
import com.jogger.http.service.ApiService

object HomeDataSource {
    private val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
    suspend fun getSubcribeArticles() = mService.getSubcribeArticles()
}