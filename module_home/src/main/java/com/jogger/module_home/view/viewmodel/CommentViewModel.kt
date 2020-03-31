package com.jogger.module_home.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.CommentData
import com.jogger.entity.CommentResponse
import com.jogger.http.datasource.ArticleActionDataSource
import com.jogger.utils.MConfig

class CommentViewModel : BaseViewModel() {
    val mCommentsLiveData = MutableLiveData<CommentResponse>()
    val mCommentsMoreLiveData = MutableLiveData<CommentResponse>()
    val mCommentsFailLiveData = MutableLiveData<Any>()
    val mNewCommentLiveData = MutableLiveData<CommentData>()
    val mDeleteCommentLiveData = MutableLiveData<Any>()
    var mLastCommentId: String? = null
    var mCardId: String? = null
    fun getCommentsByCard(hot: Int?) {
        launchOnlyresult({
            ArticleActionDataSource.getCommentsByCard(mCardId!!, hot, mLastCommentId)
        }, {
            if (mLastCommentId == null) {
                mCommentsLiveData.value = it
            } else {
                mCommentsMoreLiveData.value = it
            }
        },
            {
                mCommentsFailLiveData.value = it
                defUI.toastEvent.postValue(it.errormsg)
            })
    }

    fun newComment(content: String, commentData: CommentData? = null) {
        launchOnlyresult({
            ArticleActionDataSource.newComment(mCardId!!, content, commentData?.creator?.uid)
        }, {
            if (commentData != null) {
                it.creator = MConfig.getLoginResult().user
                it.receiver = commentData.creator
            }
            mNewCommentLiveData.value = it
        }, { defUI.toastEvent.postValue(it.errormsg) }, {}, true)
    }

    fun deleteComment(commentId: String) {
        launchOnlyresult({
            ArticleActionDataSource.deleteComment(commentId)
        }, {
            mDeleteCommentLiveData.value = null
        }, { defUI.toastEvent.postValue(it.errormsg) }, {
        }, true)
    }
}