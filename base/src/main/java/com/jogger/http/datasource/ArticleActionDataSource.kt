package com.jogger.http.datasource

import com.jogger.entity.TextCard
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object ArticleActionDataSource : BaseDataSource() {
    suspend fun checkLiked(cardId: String) = mService.checkLiked(APP_VERSION, cardId)
    suspend fun newLike(cardId: String) = mService.newLike(APP_VERSION, cardId)
    suspend fun cancelLike(cardId: String) = mService.cancelLike(APP_VERSION, cardId)
    suspend fun getCommentsByCard(cardId: String, hot: Int?, lastCommentId: String?) =
        mService.getCommentsByCard(APP_VERSION, cardId, hot, lastCommentId)

    suspend fun newComment(cardId: String, content: String?, receiverId: String?) =
        mService.newComment(APP_VERSION, cardId, receiverId, content)

    suspend fun deleteComment(commentId: String) = mService.deleteComment(APP_VERSION, commentId)

    suspend fun publishTextCard(
        priv: Int?, title: String?, category: Int?, original: Int?, from: String?, content: String?,
        type: String?, originbookid: String?, pic: String?
    ) :TextCard {
        val file = File(pic)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("pic", "pic.jpg", requestFile)
        val apply = HashMap<String, Any?>().apply {
            put("v", APP_VERSION)
            put("priv", priv)
            put("title", title)
            put("category", category)
            put("original", original)
            put("from", from)
            put("content", content)
            put("type", type)
            put("originbookid", originbookid)
        }
        return mService.publishTextCard(
            apply,
            body
        )
    }

}