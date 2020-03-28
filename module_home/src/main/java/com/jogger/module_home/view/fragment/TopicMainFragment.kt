package com.jogger.module_home.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.base.BaseFragment
import com.jogger.module_home.R
import com.jogger.module_home.adapter.TopicMainAdapter
import com.jogger.module_home.databinding.HomeFragmentTopicMainBinding
import com.jogger.module_home.view.viewmodel.TopicMainViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.home_fragment_topic_main.*

/**
 * Created by jogger on 2020/3/27
 * 描述：
 */

class TopicMainFragment :
    BaseFragment<TopicMainViewModel, HomeFragmentTopicMainBinding>(),
    OnItemClickListener, OnRefreshLoadMoreListener {

    companion object {
        const val TYPE_ALL = 0
        const val TYPE_HOT = 1
        fun getInstance(type: Int, cardId: String): TopicMainFragment {
            val fragment = TopicMainFragment()
            val bundle = Bundle()
            bundle.putInt(ex.TYPE, type)
            bundle.putString(ex.CARD_ID, cardId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val mAdapter: TopicMainAdapter by lazy {
        TopicMainAdapter().apply {
            setOnItemClickListener(this@TopicMainFragment)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        val type = arguments!!.getInt(ex.TYPE, TYPE_ALL)
        if (type == TYPE_HOT)
            mViewModel.mRefreshExtra = "{\"hot\":\"1\"}"
        mViewModel.mCardId = arguments!!.getString(ex.CARD_ID)
        rv_content.layoutManager = object : LinearLayoutManager(mContext) {
//            override fun canScrollVertically(): Boolean {
////                return false
////            }
        }
        rv_content.adapter = mAdapter
        srl_refresh.setOnRefreshLoadMoreListener(this)
        mViewModel.mTextCardsLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            mAdapter.setNewInstance(it)
            if (it == null || it.isEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            }
        })
        mViewModel.mTextCardsMoreLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it == null || it.isEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            } else {
                mAdapter.addData(it)
            }
        })
        mViewModel.mTextCardsFailLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
        })
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        mViewModel.getCardInTopic()
    }

    override fun layoutId() = R.layout.home_fragment_topic_main

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        lazyLoadData()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.mMoreExtra = null
        lazyLoadData()
    }
}