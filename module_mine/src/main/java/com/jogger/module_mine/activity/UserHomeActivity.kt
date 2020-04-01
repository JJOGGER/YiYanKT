package com.jogger.module_mine.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.entity.UserHomeData
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineActivityUserHomeBinding
import com.jogger.module_mine.fragment.UserHomeFragment
import com.qmuiteam.qmui.kotlin.onClick
import ex.USER_HOME_PAGE

@Route(path = USER_HOME_PAGE)
class UserHomeActivity : BaseActivity<BaseViewModel, MineActivityUserHomeBinding>() {
    override fun getLayoutId() = R.layout.mine_activity_user_home

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.addLeftBackImageButton().onClick {finish() }
        val userData: UserHomeData? = intent.getParcelableExtra(ex.USER_DATA)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_user_home,
                if (userData == null) UserHomeFragment.getInstance(intent.getStringExtra(ex.UID)) else UserHomeFragment.getInstance(
                    userData
                )
            )
            .commit()
    }
}
