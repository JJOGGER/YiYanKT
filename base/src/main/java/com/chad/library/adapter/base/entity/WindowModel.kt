package com.chad.library.adapter.base.entity

import ex.OnFunctionWindowClickListener

/**
 * Created by jogger on 2020/3/30
 * 描述：
 */

data class WindowModel(
    val text: String,
    val type: Int = TYPE_COMMON,
    val listener: OnFunctionWindowClickListener?=null
) {
    companion object {
        const val TYPE_ERROR = -1
        const val TYPE_COMMON = 0
    }
}