package com.jogger.yiyan.view.splash

import android.os.Bundle
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.utils.LogUtils
import com.jogger.widget.YiYanHeader
import com.jogger.yiyan.R
import com.jogger.yiyan.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    override fun init(savedInstanceState: Bundle?) {
        LogUtils.e("----------SplashActivity")
        mTopBar.setTitle("xxx")
        srl_refresh.setRefreshHeader(YiYanHeader(mContext))
        srl_refresh.setOnRefreshListener { refreshLayout ->
            srl_refresh.postDelayed({ srl_refresh.closeHeaderOrFooter() }, 5000)
        }
    }

    //    lateinit var mBinding: ActivitySplashBinding
    override fun getLayoutId(): Int =
        R.layout.activity_splash

}
