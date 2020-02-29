package com.jogger.http.basic

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
class BaseResponse<T> {
    var errorcode: Int = 0

    var msg: String? = null

    var data: T? = null

    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + errorcode +
                ", msg='" + msg + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}