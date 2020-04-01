package com.jogger.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.UserDataSource

/**
 * Created by jogger on 2020/4/1
 * 描述：
 */
class BookListViewModel : BaseViewModel() {
    val mCardsLiveData = MutableLiveData<MutableList<TextCard>>()
    val mCardsMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mCardsFailLiveData = MutableLiveData<Any?>()
    var mUid: String? = null
    lateinit var mBookId: String
    var mLastDateTime: String? = null
    var mInvent: Int? = null
    fun getTextCardByUser() {
        launchOnlyresult({
            UserDataSource.getTextCardByUser(mUid!!, mLastDateTime, mInvent)
        }, {
            if (mLastDateTime == null) {
                mCardsLiveData.value = it.textcardlist
            } else {
                mCardsMoreLiveData.value = it.textcardlist
            }
            if (!it.textcardlist.isNullOrEmpty()) {
                mLastDateTime = it.textcardlist!![it.textcardlist!!.size - 1].datetime
            }
        }, {
            defUI.toastEvent.postValue(it.errormsg)
            mCardsFailLiveData.value = null
        })
    }

    fun getTextCardByBook() {
        launchOnlyresult({
            UserDataSource.getTextCardByBook(mBookId, mLastDateTime)
        }, {
            if (mLastDateTime == null) {
                mCardsLiveData.value = it.textcardlist
            } else {
                mCardsMoreLiveData.value = it.textcardlist
            }
            if (!it.textcardlist.isNullOrEmpty()) {
                mLastDateTime = it.textcardlist!![it.textcardlist!!.size - 1].datetime
            }
        }, {
            defUI.toastEvent.postValue(it.errormsg)
            mCardsFailLiveData.value = null
        })
    }
}