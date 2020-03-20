package com.jogger.module_star.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.entity.UserData
import com.jogger.http.datasource.HomeDataSource
import org.json.JSONObject

class SearchViewModel : BaseViewModel() {
    val mSearchTextCardLiveData = MutableLiveData<MutableList<TextCard>>()
    val mSearchUsersLiveData = MutableLiveData<MutableList<UserData>>()
    var mTextIndex: Int? = null
    var mUserIndex: Int? = null
    var mMoreExtra: String? = null
    fun searchTextCards(content: String) {
        launchOnlyresult({
            HomeDataSource.searchTextCards(content, mTextIndex, mMoreExtra)
        }, {
            mSearchTextCardLiveData.value = it.textcardlist
            mMoreExtra = it.moreextra
            val jsonObject = JSONObject(mMoreExtra)
            mTextIndex = jsonObject.getInt("offset")
        }, {
            defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}")
        }, {}, true)
    }

    fun searchUsers(content: String) {
        launchOnlyresult({
            HomeDataSource.searchUsers(content, mUserIndex)
        }, {
            mSearchUsersLiveData.value = it.userlist
        }, { defUI.toastEvent.postValue("${it.errorcode}:${it.errormsg}") }, {})
    }
}