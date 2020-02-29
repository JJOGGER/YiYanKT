package com.jogger.http.basic.exception

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
open class BaseException : RuntimeException {

    var errorCode = -1

    constructor(errorCode: Int, errorMessage: String) : super(errorMessage) {
        this.errorCode = errorCode
    }

    override val message: String?
        get() = super.message
}