package com.jogger.http.datasource

import com.jogger.http.basic.RetrofitManager
import com.jogger.http.service.ApiService

object HomeDataSource {
    private const val APP_VERSION = "3.35"
    private val mService: ApiService = RetrofitManager.getService(ApiService::class.java)
    suspend fun getSubcribeArticles(feedid: String?) = mService.getSubcribeArticles(APP_VERSION, feedid)

    suspend fun getTextCardsByType(type: Int) = mService.getStarTextCardsByType(APP_VERSION, type)
    suspend fun getAllStarTextCards() = mService.getAllStarTextCards(APP_VERSION)
    suspend fun getOriginStarTextCards() = mService.getOriginStarTextCards(APP_VERSION)
}