package com.jogger.module_home.view.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.module_home.R
import ex.INDEX
import kotlinx.android.synthetic.main.home_fragment_subscribe.*

/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class SubscribeFragment : BaseFragment<BaseViewModel, ViewDataBinding>() {
    companion object {
        fun newInstance(ext: Int): SubscribeFragment {
            val subscribeFragment = SubscribeFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, ext)
            subscribeFragment.arguments = bundle
            return subscribeFragment
        }
    }

    override fun layoutId(): Int = R.layout.home_fragment_subscribe

    override fun initView(savedInstanceState: Bundle?) {
        if (arguments != null) {
            val index = arguments!!.getInt(INDEX)
            tv_title.setText("---${index}")
        }

    }
}