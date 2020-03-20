package com.jogger.module_home.view.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.ArticleData
import com.jogger.entity.TextCard
import com.jogger.http.datasource.HomeDataSource
import com.jogger.utils.LogUtils
import kotlinx.coroutines.CoroutineScope

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextCardDetailViewModel : BaseViewModel() {
    val mTextCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mTextCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mTextCardsFailureLiveData = MutableLiveData<Any>()
    var mLastCardId: String? = null
    var mMoreExtra: String? = null
    fun getSubcribeArticles(feedid: String?) {
        launchOnlyresult({ HomeDataSource.getSubcribeArticles(feedid) }, {
            LogUtils.e("-----success${it}")
            if (TextUtils.isEmpty(feedid)) {
                mTextCardsLiveData.value = it.textcardlist
            } else {
                mTextCardsMoreLiveData.value = it.textcardlist
            }

        },
            {
                defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
                mTextCardsFailureLiveData.value = it.errorcode
            })
    }

    fun getTextCardsByType(type: Int) {
        var block: suspend CoroutineScope.() -> ArticleData
        when (type) {
            CARD_CATEGORY.TYPE_ALL._value -> {
                block = { HomeDataSource.getAllStarTextCards(mLastCardId, mMoreExtra) }
            }
            CARD_CATEGORY.TYPE_ORIGIN._value -> {
                block = { HomeDataSource.getOriginStarTextCards(mLastCardId) }
            }
            else ->
                block = { HomeDataSource.getTextCardsByType(type, mLastCardId, mMoreExtra) }
        }
        launchOnlyresult(block, {
            if (TextUtils.isEmpty(mMoreExtra)) {
                mTextCardsLiveData.value = it.textcardlist
            } else {
                mTextCardsMoreLiveData.value = it.textcardlist
            }
        }, {
            defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
            mTextCardsFailureLiveData.value = it.errorcode
        })
    }
}