package com.jogger.module_home.view.delegate

import android.content.Context

abstract class BaseProxy<T>(binding: T, context: Context) {
    val mBinding: T
    val mContext: Context

    init {
        mBinding = binding
        mContext = context
    }

    abstract fun initView()

}