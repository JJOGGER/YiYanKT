package com.jogger.module_home.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.http.datasource.HomeDataSource
import com.jogger.utils.LogUtils

class HomeViewModel : BaseViewModel() {
    val mSubcribeArticlesLiveData = MutableLiveData<List<TextCard>>()
    fun getSubcribeArticles() {
        launchOnlyresult({ HomeDataSource.getSubcribeArticles() }, {
            LogUtils.e("-----success${it}")
            mSubcribeArticlesLiveData.value = it.textcardlist
        })
    }
}