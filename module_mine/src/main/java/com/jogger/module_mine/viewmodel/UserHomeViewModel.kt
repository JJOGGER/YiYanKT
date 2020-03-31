package com.jogger.module_mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.UserHomeData
import com.jogger.http.datasource.UserDataSource

/**
 * Created by jogger on 2020/3/31
 * 描述：
 */
class UserHomeViewModel : BaseViewModel() {
    val mUserHomeDataLiveData by lazy { MutableLiveData<UserHomeData>() }
    fun getUserInfo(uid: String) {
        launchOnlyresult({
            UserDataSource.getUserinfoAndBooklist(uid)
        }, {
            mUserHomeDataLiveData.value = it
        })
    }
}