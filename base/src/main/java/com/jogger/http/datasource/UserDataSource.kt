package com.jogger.http.datasource

object UserDataSource : BaseDataSource() {
    suspend fun getUserinfoAndBooklist(uid: String) = mService.getUserinfoAndBooklist(APP_VERSION, uid)
    suspend fun getTextCardByUser(uid: String, dateTime: String?, jo: Int?) =
        mService.getTextCardByUser(APP_VERSION, uid, dateTime, jo)

    suspend fun getTextCardByBook(bookId: String, dateTime: String?) =
        mService.getTextCardByBook(APP_VERSION, bookId, dateTime)
}