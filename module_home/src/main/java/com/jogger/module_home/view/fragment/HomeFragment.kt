package com.jogger.module_home.view.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.entity.TextCard
import com.jogger.manager.QDSkinManager
import com.jogger.module_home.R
import com.jogger.module_home.adapter.HomePagerAdapter
import com.jogger.module_home.view.viewmodel.HomeViewModel
import com.jogger.widget.YiYanHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.MODULE_HOME_MAIN
import kotlinx.android.synthetic.main.home_fragment_home.*


/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
@Route(path = MODULE_HOME_MAIN)
class HomeFragment : BaseFragment<HomeViewModel, ViewDataBinding>(), OnRefreshLoadMoreListener {


    private lateinit var mHomeAdapter: HomePagerAdapter
    private var mFeedId: String? = ""
    override fun initView(savedInstanceState: Bundle?) {
        topbar.setTitle("订阅")
        topbar.addRightImageButton(R.drawable.icons8_time_machine_66_3x, R.id.clock_funciton)
        QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
        mHomeAdapter = HomePagerAdapter(vp_content)
        srl_refresh.setRefreshHeader(YiYanHeader(mContext!!))
        srl_refresh.setOnRefreshLoadMoreListener(this)
        mViewModel.mSubcribeArticlesLiveData.observe(this, Observer { handleArticles(it) })
        mViewModel.mSubcribeArticlesMoreLiveData.observe(this, Observer { handleMoreArticles(it) })
        mViewModel.mSubcribeArticlesFailureLiveData.observe(this, Observer { handleArticlesFailure(it) })
    }

    private fun handleArticlesFailure(it: Any?) {
        srl_refresh.closeHeaderOrFooter()
    }

    private fun handleMoreArticles(cards: List<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        if (cards.isEmpty()) {
            srl_refresh.finishLoadMoreWithNoMoreData()
        } else {
            mHomeAdapter.addDatas(cards)
            mFeedId = cards[cards.size - 1].feedid!!
        }
    }

    private fun handleArticles(cards: List<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        mHomeAdapter.setDatas(cards)
        vp_content.adapter = mHomeAdapter
    }

    override fun lazyLoadData() {
        mViewModel.getSubcribeArticles(mFeedId)
    }

    override fun layoutId(): Int = R.layout.home_fragment_home


    override fun onRefresh(refreshLayout: RefreshLayout) {
        lazyLoadData()
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        vp_content.postDelayed({
            lazyLoadData()
        }, 300)
    }

}