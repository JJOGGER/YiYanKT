package com.jogger.yiyan.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jogger.base.BaseViewModel
import com.jogger.entity.UserHomeData
import com.jogger.entity.request.LoginRequest
import com.jogger.http.basic.exception.ExceptionHandle
import com.jogger.http.datasource.LoginDataSource
import com.jogger.utils.LogUtils
import com.jogger.utils.MConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * Created by jogger on 2020/3/17
 * 描述：
 */
class LoginViewModel : BaseViewModel() {
    val mLoginLiveData = MutableLiveData<UserHomeData>()
    var mLoginRequest: LoginRequest? = null
    @ExperimentalCoroutinesApi
    fun loginFromOtherPlatform(uid: String, token: String) {
        launchUI {
            launchFlow {
                LoginDataSource.getOtherPlatformUserInfo(uid, token)
            }.flatMapConcat {
                mLoginRequest = LoginRequest(
                    uid, it.name!!,
                    it.profile_image_url!!, it.avatar_large!!, it.gender!!, "4593bcb2562d3c5a5955fbe79e7391f3", "3"
                )
                launchFlow {
                    LoginDataSource.loginFromOtherPlatform(mLoginRequest!!)
                }
            }.onStart { defUI.showDialog.postValue(null) }
                .flowOn(Dispatchers.IO)
                .onCompletion { defUI.dismissDialog.call() }
                .catch {
                    val err = ExceptionHandle.handleException(it)
                    LogUtils.e("${err.errorcode}: ${err.errormsg}")
                }
                .collect {
                    LogUtils.e("-----${it}")
                    MConfig.setLoginResult(it)
                    mLoginLiveData.value = it
                }
        }

//        launchOnlyresult({
//            HomeDataSource.loginFromOtherPlatform(mLoginRequest!!)
//        },
//            {
//                mLoginLiveData.value = it
//            })
    }
}