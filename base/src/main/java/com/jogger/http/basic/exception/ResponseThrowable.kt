package com.jogger.http.basic.exception

/**
 *   @auther : Aleyn
 *   time   : 2019/08/12
 */
open class ResponseThrowable : Exception {
    var errorcode: Int
    var errormsg: String

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        errorcode = error.getKey()
        errormsg = error.getValue()
    }

    constructor(code: Int, msg: String, e: Throwable? = null) : super(e) {
        this.errorcode = code
        this.errormsg = msg
    }
}

