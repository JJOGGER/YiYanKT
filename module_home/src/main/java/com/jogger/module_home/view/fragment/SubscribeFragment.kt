package com.jogger.module_home.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.manager.QDSkinManager
import com.jogger.module_home.R
import ex.INDEX
import ex.showToast
import kotlinx.android.synthetic.main.home_fragment_subscribe.*

/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class SubscribeFragment : BaseFragment<BaseViewModel, ViewDataBinding>() {
    private  var index:Int?=null
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
             index = arguments!!.getInt(INDEX)
            tv_title.setText("---${index}")
        }
        btn_test.setOnClickListener {
            Log.e(SubscribeFragment.javaClass.simpleName,"------->xxx")
            showToast("---${index}")
            if(index==1){
                QDSkinManager.changeSkin(0)
            }
            if(index==2){
                QDSkinManager.changeSkin( 1)
            }
            if(index==3){
                QDSkinManager.changeSkin(2)
            }
            if(index==4){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
            }
            if(index==5){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}