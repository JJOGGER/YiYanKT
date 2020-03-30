package com.chad.library.adapter.base.entity

/**
 * Created by jogger on 2020/3/30
 * 描述：
 */
const val TYPE_ERROR = -1
const val TYPE_COMMON = 0

data class WindowModel(
    val text: String,
    val type: Int = 0
)