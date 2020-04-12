package com.jogger.module_home.view.delegate

import android.app.Activity
import androidx.databinding.ObservableField
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.ArticleActionDataSource
import com.jogger.module_home.databinding.HomeDetailBottomActionBinding
import com.jogger.module_home.view.activity.CommentActivity
import com.jogger.utils.ToastHelper
import ex.*

abstract class BaseProxy<T>(binding: T, context: Activity) : BaseViewModel() {
    val mBinding: T
    val mContext: Activity
    val mTextCardObservable = ObservableField<TextCard>()
    var mBottomBinding: HomeDetailBottomActionBinding? = null
    private var mIsLike = false

    init {
        mBinding = binding
        mContext = context
    }

    abstract fun initView()


    fun setupBottomAction(binding: HomeDetailBottomActionBinding) {
        mBottomBinding = binding
    }


    fun checkLiked(cardId: String) {
        launchOnlyresult({
            ArticleActionDataSource.checkLiked(cardId)
        }, {
            mIsLike = (it.l == 1)
            if (mBottomBinding != null)
                mBottomBinding!!.tvLike.isSelected = mIsLike
        })
    }

    /**
     * 收藏
     */
    fun collectArticle(cardId: String) {
        launchOnlyresult({
            ArticleActionDataSource.newLike(cardId)
        }, {})
    }

    /**
     * 同感
     */
    fun likeArticle(cardId: String) {
        mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.tvLike.isSelected
        if (!mIsLike) {
            launchOnlyresult({
                ArticleActionDataSource.newLike(cardId)
            }, {
                mIsLike = !mIsLike
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = mIsLike
                    mBottomBinding!!.tvLike.text = (mBottomBinding!!.tvLike.text.toString().toInt() + 1).toString()
                }
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        } else {
            launchOnlyresult({
                ArticleActionDataSource.cancelLike(cardId)
            }, {
                mIsLike = !mIsLike
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = mIsLike
                    mBottomBinding!!.tvLike.text = (mBottomBinding!!.tvLike.text.toString().toInt() - 1).toString()
                }
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        }

    }


    fun toComments(card: TextCard) {
        CommentActivity.navTo(mContext, card.textcardid!!, card.commentcnt, false)
    }

    fun finish() {
        mContext.finish()
    }

    fun toUserHomePage(uid: String) {
        val map = HashMap<String, Any>()
        map.put(UID, uid)
        toActivity(mContext, USER_HOME_PAGE, map)
    }

    fun toBookPage(uid: String, title: String) {
        val map = HashMap<String, Any>()
        map.put(UID, uid)
        map.put(TITLE, title)
        toActivity(mContext, USER_BOOK_PAGE, map)
    }
}