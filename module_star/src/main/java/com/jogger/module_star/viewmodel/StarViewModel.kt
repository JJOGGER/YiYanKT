package com.jogger.module_star.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.ArticleData
import com.jogger.entity.TextCard
import com.jogger.http.datasource.HomeDataSource
import kotlinx.coroutines.CoroutineScope

/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class StarViewModel : BaseViewModel() {
    val mSubcribeTextCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeTextCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeTextCardsFailureLiveData = MutableLiveData<Any>()
    fun getTextCardsByType(type: Int,feedid: String?) {
        var block: suspend CoroutineScope.() -> ArticleData
        when (type) {

            CARD_CATEGORY.TYPE_ALL._value -> {
                block = { HomeDataSource.getAllStarTextCards() }
            }
            CARD_CATEGORY.TYPE_ORIGIN._value -> {
                block = { HomeDataSource.getOriginStarTextCards() }
            }
            else ->
                block = { HomeDataSource.getTextCardsByType(type) }
        }
        launchOnlyresult(block, {
            if (TextUtils.isEmpty(feedid)) {
                mSubcribeTextCardsLiveData.value = it.textcardlist
            } else {
                mSubcribeTextCardsMoreLiveData.value = it.textcardlist
            }
        }, {
            defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
            mSubcribeTextCardsFailureLiveData.value = it.code
        })
    }
}