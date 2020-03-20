package com.jogger.http.basic.exception

import com.jogger.http.basic.config.HttpCode

/**
 * Created by jogger on 2020/3/16
 * 描述：
 */
class ForbiddenException : ResponseThrowable(HttpCode.CODE_PARAMETER_INVALID, "404错误")