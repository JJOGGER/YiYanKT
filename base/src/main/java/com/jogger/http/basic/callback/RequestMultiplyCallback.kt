package com.jogger.http.basic.callback

import com.jogger.http.basic.exception.BaseException

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
interface RequestMultiplyCallback<T> : RequestCallback<T> {
    fun onFail(e: BaseException)
}