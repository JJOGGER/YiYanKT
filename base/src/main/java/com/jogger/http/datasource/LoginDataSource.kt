package com.jogger.http.datasource

import com.jogger.entity.request.LoginRequest

object LoginDataSource : BaseDataSource() {
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