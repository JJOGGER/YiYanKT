package com.jogger.module_mine.module

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.module_mine.R
import com.jogger.module_mine.fragment.MineFragment


class MineTestActivity : BaseActivity<BaseViewModel, ViewDataBinding>() {
    override fun getLayoutId() = R.layout.mine_activity_mine_test

    override fun init(savedInstanceState: Bundle?) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.fl_content, MineFragment())
            .commitAllowingStateLoss()
    }

}
