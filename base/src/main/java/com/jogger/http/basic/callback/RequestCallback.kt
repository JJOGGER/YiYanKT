package com.jogger.http.basic.callback

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
interface RequestCallback<T> {
    fun onSuccess(t: T)
}