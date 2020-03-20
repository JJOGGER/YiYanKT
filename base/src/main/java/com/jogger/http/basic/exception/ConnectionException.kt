package com.jogger.http.basic.exception

import com.jogger.http.basic.config.HttpCode

/**
 * Created by jogger on 2020/3/16
 * 描述：
 */
class ConnectionException : ResponseThrowable(HttpCode.CODE_CONNECTION_FAILED, "网络连接失败") {
}