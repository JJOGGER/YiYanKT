package com.jogger.http.datasource

object ArticleActionDataSource : BaseDataSource() {
    suspend fun checkLiked(cardId: String) = mService.checkLiked(APP_VERSION, cardId)
    suspend fun newLike(cardId: String) = mService.newLike(APP_VERSION, cardId)
    suspend fun cancelLike(cardId: String) = mService.cancelLike(APP_VERSION, cardId)
    suspend fun getCommentsByCard(cardId: String, hot: Int?, lastCommentId: String?) =
        mService.getCommentsByCard(APP_VERSION, cardId, hot, lastCommentId)

    suspend fun newComment(cardId: String, content: String?, receiverId: String?) =
        mService.newComment(APP_VERSION, cardId, receiverId, content)

    suspend fun deleteComment(commentId: String) = mService.deleteComment(APP_VERSION, commentId)
}