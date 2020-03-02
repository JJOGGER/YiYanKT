package com.jogger.module_home.view.viewmodel

import com.jogger.base.BaseViewModel
import com.jogger.http.datasource.HomeDataSource
import com.jogger.utils.LogUtils

class HomeViewModel : BaseViewModel() {

    fun getSubcribeArticles(){
        launchOnlyresult({HomeDataSource.getSubcribeArticles()},{
            LogUtils.e("-----success${it}")
        })
    }
}