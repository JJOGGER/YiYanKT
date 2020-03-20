package com.jogger.http.basic.exception

import com.jogger.http.basic.config.HttpCode

/**
 * Created by jogger on 2020/3/16
 * 描述：
 */
class ResultInvalidException:ResponseThrowable(HttpCode.CODE_RESULT_INVALID, "无效请求") {
}