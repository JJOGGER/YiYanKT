package com.jogger.module_mine.fragment

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineFragmentMineBinding
import com.jogger.utils.MConfig
import ex.MODULE_MINE_MAIN
import kotlinx.android.synthetic.main.mine_fragment_mine.*

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
@Route(path = MODULE_MINE_MAIN)
class MineFragment : BaseFragment<BaseViewModel, MineFragmentMineBinding>() {
    override fun layoutId() = R.layout.mine_fragment_mine

    override fun initView(savedInstanceState: Bundle?) {
        srl_refresh.setEnableLoadMore(false)
        srl_refresh.setOnRefreshListener({

        })
        val loginResult = MConfig.getLoginResult()
        mBinding!!.topbar.setBottomDividerAlpha(0)
        mBinding!!.userHeader.userData = loginResult.user
        var cardCount = 0
        loginResult.booklist?.filter {
            it.cardcnt != 0
        }?.forEach {
            cardCount = cardCount + it.cardcnt
        }
        mBinding!!.userHeader.cardCount = cardCount.toString()
    }
}