package com.jogger.http.datasource

object ArticleActionDataSource : BaseDataSource() {
    suspend fun checkLiked(cardId: String) = mService.checkLiked(APP_VERSION, cardId)
    suspend fun newLike(cardId: String) = mService.newLike(APP_VERSION, cardId)
    suspend fun cancelLike(cardId: String) = mService.cancelLike(APP_VERSION, cardId)

}