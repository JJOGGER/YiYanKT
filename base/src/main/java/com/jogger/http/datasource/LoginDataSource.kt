package com.jogger.http.datasource

import com.jogger.entity.request.LoginRequest
import com.jogger.http.basic.RetrofitManager
import com.jogger.http.service.ApiService

object LoginDataSource {
    private const val APP_VERSION = "3.35"
    private val mService: ApiService = RetrofitManager.getService(ApiService::class.java)

    suspend fun getOtherPlatformUserInfo(uid: String, token: String) = mService.getOtherPlatformUserInfo(token, uid)
    suspend fun loginFromOtherPlatform(loginRequest: LoginRequest) = mService.loginFromOtherPlatform(
        APP_VERSION,
        loginRequest.platuid,
        loginRequest.username,
        loginRequest.smallavatar,
        loginRequest.largeavatar,
        loginRequest.gender,
        loginRequest.ac,
        loginRequest.actype
    )

}