package com.jogger.module_home.view.delegate

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextProxy : TextCardDetailDelegate {
    override fun initView() {
    }

    override fun getProxy(): TextCardDetailDelegate {
        return TextProxy()
    }
}