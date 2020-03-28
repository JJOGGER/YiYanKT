package com.jogger.module_home.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.HomeDataSource

/**
 * Created by jogger on 2020/3/27
 * 描述：
 */
class TopicMainViewModel : BaseViewModel() {
    val mTextCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mTextCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mTextCardsFailLiveData = MutableLiveData<Any>()
    var mRefreshExtra: String? = null
    var mMoreExtra: String? = null
    var mCardId: String? = null
    fun getCardInTopic() {
        launchOnlyresult({
            HomeDataSource.getCardInTopic(mCardId!!, mRefreshExtra, mMoreExtra)
        }, {
            if (mMoreExtra == null) {
                mTextCardsLiveData.value = it.textcardlist
            } else {
                mTextCardsMoreLiveData.value = it.textcardlist
            }
            mMoreExtra = it.moreextra
        }, {
            mTextCardsFailLiveData.value = null
        })
    }
}