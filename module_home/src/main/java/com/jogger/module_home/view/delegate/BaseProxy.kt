package com.jogger.module_home.view.delegate

import android.content.Context
import androidx.databinding.ObservableField
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.ArticleActionDataSource
import com.jogger.module_home.databinding.HomeDetailBottomActionBinding
import com.jogger.utils.ToastHelper
import ex.showActionAnimation

abstract class BaseProxy<T>(binding: T, context: Context) : BaseViewModel() {
    val mBinding: T
    val mContext: Context
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
        if (mIsLike) {
            launchOnlyresult({
                ArticleActionDataSource.newLike(cardId)
            }, {
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
                    mBottomBinding!!.tvLike.text = (mBottomBinding!!.tvLike.text.toString().toInt() - 1).toString()
                }
                mIsLike=!mIsLike
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        } else {
            launchOnlyresult({
                ArticleActionDataSource.cancelLike(cardId)
            }, {
                if (mBottomBinding != null) {
                    showActionAnimation(mBottomBinding!!.ibtnLike)
                    mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
                    mBottomBinding!!.tvLike.text = (mBottomBinding!!.tvLike.text.toString().toInt() + 1).toString()
                }
                mIsLike=!mIsLike
            }, {
                ToastHelper.showToast(it.errormsg)
                mBottomBinding!!.ibtnLike.isSelected = !mBottomBinding!!.ibtnLike.isSelected
            })
        }

    }

}