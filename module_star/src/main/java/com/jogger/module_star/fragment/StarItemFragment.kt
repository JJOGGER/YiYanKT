package com.jogger.module_star.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.base.BaseFragment
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.module_star.R
import com.jogger.adapter.CommonCardAdapter
import com.jogger.module_star.viewmodel.StarViewModel
import com.jogger.widget.YiYanHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.*
import kotlinx.android.synthetic.main.star_fragment_item.*

/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class StarItemFragment : BaseFragment<StarViewModel, ViewDataBinding>(), OnRefreshLoadMoreListener,
    OnItemClickListener {

    private var mType = CARD_CATEGORY.TYPE_ALL._value
    private val mAdapter: CommonCardAdapter by lazy {
        CommonCardAdapter(null, mType).apply {
            setOnItemClickListener(this@StarItemFragment)
        }
    }

    override fun layoutId(): Int = R.layout.star_fragment_item

    companion object {
        fun getInstance(type: Int): StarItemFragment {
            val fragment = StarItemFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        srl_refresh.setRefreshHeader(YiYanHeader(mContext!!))
        mType = arguments!!.getInt(INDEX)
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
        srl_refresh.setOnRefreshLoadMoreListener(this)
        mViewModel.mSubcribeTextCardsLiveData.observe(this, Observer { handleTextCards(it) })
        mViewModel.mSubcribeTextCardsMoreLiveData.observe(this, Observer { handleMoreTextCards(it) })
        mViewModel.mSubcribeTextCardsFailureLiveData.observe(this, Observer { handleTextCardsFailure(it) })
    }

    private fun handleTextCardsFailure(it: Any?) {
        srl_refresh.closeHeaderOrFooter()
    }

    private fun handleMoreTextCards(cards: List<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        if (cards.isEmpty()) {
            srl_refresh.finishLoadMoreWithNoMoreData()
        } else {
            mAdapter.addData(cards)
        }
    }

    private fun handleTextCards(cards: MutableList<TextCard>) {
        srl_refresh.closeHeaderOrFooter()
        mAdapter.setNewData(cards)
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        srl_refresh.autoRefresh()
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.getTextCardsByType(mType)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        rv_content.postDelayed({
            mViewModel.getTextCardsByType(mType)
        }, 300)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.getItem(position) as TextCard
        val map = HashMap<String, Any>()
        if (item.category == CARD_CATEGORY.TYPE_TOPIC._value) {
            map.put(TEXT_CARD, item)
            toActivity(mContext!!, TOPIC_MIAN_DETAIL, map)
            return
        }
        map.put(POSITION, position)
        map.put(FROM_PAGE, PAGE_STAR)
        map.put(TEXT_CARDS, mAdapter.data)
        toActivity(mContext!!, TEXT_CARD_DETAIL, map)
    }
}