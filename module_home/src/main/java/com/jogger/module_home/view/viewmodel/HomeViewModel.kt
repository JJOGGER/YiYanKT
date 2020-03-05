package com.jogger.module_home.view.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.HomeDataSource
import com.jogger.utils.LogUtils

class HomeViewModel : BaseViewModel() {
    val mSubcribeArticlesLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeArticlesMoreLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSubcribeArticlesFailureLiveData = MutableLiveData<Any>()
    fun getSubcribeArticles(feedid: String?) {
        launchOnlyresult({ HomeDataSource.getSubcribeArticles(feedid) }, {
            LogUtils.e("-----success${it}")
            if (TextUtils.isEmpty(feedid)) {
                mSubcribeArticlesLiveData.value = it.textcardlist
            } else {
                mSubcribeArticlesMoreLiveData.value = it.textcardlist
            }

        },
            {
                defUI.toastEvent.postValue("${it.code}:${it.errMsg}")
                mSubcribeArticlesFailureLiveData.value = it.code
            })
    }
}