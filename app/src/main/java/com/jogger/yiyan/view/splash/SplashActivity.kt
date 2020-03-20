package com.jogger.yiyan.view.splash

import android.os.Bundle
import android.text.TextUtils
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.utils.MConfig
import com.jogger.yiyan.MainActivity
import com.jogger.yiyan.R
import com.jogger.yiyan.databinding.ActivitySplashBinding
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import ex.SINA_APP_KEY
import ex.SINA_REDIRECT_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    val SCOPE = (
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write")

    override fun init(savedInstanceState: Bundle?) {
        val authInfo = AuthInfo(mContext, SINA_APP_KEY, SINA_REDIRECT_URL, SCOPE)
        WbSdk.install(mContext, authInfo)
        GlobalScope.launch {
            delay(1000)
            GlobalScope.launch(Dispatchers.Main) {
                if (TextUtils.isEmpty(MConfig.getCookie())) {
                    navTo(mContext, LoginActivity::class.java)
                } else {
                    navTo(mContext, MainActivity::class.java)
                }
            }
        }
    }

    override fun hasTitleAction(): Boolean {
        return false
    }

    override fun isFullScreen(): Boolean {
        return true
    }

    //    lateinit var mBinding: ActivitySplashBinding
    override fun getLayoutId(): Int =
        R.layout.activity_splash

}
