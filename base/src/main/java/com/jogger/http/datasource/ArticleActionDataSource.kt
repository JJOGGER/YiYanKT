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
    suspend fun deleteCard(cardId: String) = mService.deleteCard(APP_VERSION, cardId, null, 0)
    suspend fun publishTextCard(
        priv: Int?, title: String?, category: Int?, original: Int?, from: String?, content: String?,
        type: String?, originbookid: String?, pic: String?
    ): TextCard {
        val file = File(pic)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("pic", "pic.jpg", requestFile)
        val textType = MediaType.parse("text/plain") // 文本类型
        val p0 = RequestBody.create(textType, APP_VERSION)
        val p1 = RequestBody.create(textType, priv.toString())
        val p2 = RequestBody.create(textType, title.toString())
        val p3 = RequestBody.create(textType, category.toString())
        val p4 = RequestBody.create(textType, original.toString())
        val p5 = RequestBody.create(textType, from.toString())
        val p6 = RequestBody.create(textType, content.toString())
        val p7 = RequestBody.create(textType, type.toString())
        val p8 = RequestBody.create(textType, originbookid.toString())
        return mService.publishTextCard(
            p0,
            p1, p2, p3, p4, p5, p6, p7, p8, body
        )
    }

    suspend fun disLikeCard(cardId: String) {
        mService.disLikeCard(APP_VERSION, cardId)
    }

    suspend fun shielduser(uid: String) {
        mService.shielduser(APP_VERSION, uid, 0)
    }
}