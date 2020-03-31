package com.jogger.utils

import com.jogger.entity.UserHomeData

/**
 * Created by jogger on 2020/3/18
 * 描述：
 */
private const val LOGIN_RESULT = "login_result"
private const val COOKIE = "cookie"

class MConfig {
    companion object {
        fun setLoginResult(userHomeData: UserHomeData) {
            MMKVUtil.getInstance().setValue(LOGIN_RESULT, GsonUtil.toJson(userHomeData))
        }

        fun getLoginResult(): UserHomeData {
            val loginResult =
                GsonUtil.fromJson<UserHomeData>(
                    MMKVUtil.getInstance().getStringValue(LOGIN_RESULT, ""),
                    UserHomeData::class.java
                )
            return loginResult
        }

        fun setCookie(cookie: String) {
            MMKVUtil.getInstance().setValue(COOKIE, cookie)
        }

        fun getCookie(): String {
            return MMKVUtil.getInstance().getStringValue(COOKIE, "")!!
        }
    }

}