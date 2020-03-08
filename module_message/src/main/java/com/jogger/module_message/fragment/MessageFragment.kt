package com.jogger.module_message.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.manager.AssetsManager
import com.jogger.module_message.R
import ex.MODULE_MESSAGE_MAIN
import kotlinx.android.synthetic.main.message_fragment_message.*

@Route(path = MODULE_MESSAGE_MAIN)
class MessageFragment: BaseFragment<BaseViewModel, ViewDataBinding>() {
    override fun layoutId(): Int = R.layout.message_fragment_message

    override fun initView(savedInstanceState: Bundle?) {

        tv1.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT1))
        tv2.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT2))
        tv3.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT3))
        tv4.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT4))
        tv5.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT5))
    }
}