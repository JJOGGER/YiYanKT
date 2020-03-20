package com.jogger.utils

import com.jogger.entity.LoginResult

/**
 * Created by jogger on 2020/3/18
 * 描述：
 */
private const val LOGIN_RESULT = "login_result"
private const val COOKIE = "cookie"

class MConfig {
    companion object {
        fun setLoginResult(loginResult: LoginResult) {
            MMKVUtil.getInstance().setValue(LOGIN_RESULT, GsonUtil.toJson(loginResult))
        }

        fun getLoginResult(): LoginResult {
            val loginResult =
                GsonUtil.fromJson<LoginResult>(
                    MMKVUtil.getInstance().getStringValue(LOGIN_RESULT, ""),
                    LoginResult::class.java
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