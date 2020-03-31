package com.jogger.module_mine.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.manager.QDSkinManager
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineFragmentMineBinding
import com.jogger.utils.MConfig
import ex.MODULE_MINE_MAIN

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
@Route(path = MODULE_MINE_MAIN)
class MineFragment : BaseFragment<BaseViewModel, MineFragmentMineBinding>() {
    override fun layoutId() = R.layout.mine_fragment_mine

    override fun initView(savedInstanceState: Bundle?) {

        mBinding!!.topbar.setBottomDividerAlpha(0)
        childFragmentManager.beginTransaction()
            .replace(R.id.fl_user_home, UserHomeFragment.getInstance(MConfig.getLoginResult()))
            .commit()
        mBinding!!.ibtnLightMode.setOnClickListener({
            if (QDSkinManager.getCurrentSkin() == QDSkinManager.SKIN_WHITE) {
                QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
            } else {
                QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
            }

        })
    }
}