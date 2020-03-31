package com.jogger.http.datasource

object UserDataSource : BaseDataSource() {
    suspend fun getUserinfoAndBooklist(uid: String) = mService.getUserinfoAndBooklist(APP_VERSION, uid)
    suspend fun getTextCardByUser(uid: String) = mService.getTextCardByUser(APP_VERSION, uid)
}