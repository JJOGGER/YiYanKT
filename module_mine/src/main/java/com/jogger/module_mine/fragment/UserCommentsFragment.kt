package com.jogger.module_mine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.base.BaseFragment
import com.jogger.entity.CommentData
import com.jogger.module_mine.R
import com.jogger.module_mine.activity.UserHomeActivity
import com.jogger.module_mine.adapter.UserCommentsAdapter
import com.jogger.module_mine.databinding.MineFragmentUserCommentsBinding
import com.jogger.module_mine.viewmodel.UserCommentsViewModel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.toActivity
import kotlinx.android.synthetic.main.mine_fragment_user_comments.*

/**
 * Created by jogger on 2020/4/2
 * 描述：
 */
class UserCommentsFragment : BaseFragment<UserCommentsViewModel, MineFragmentUserCommentsBinding>(),
    OnItemChildClickListener,
    OnItemClickListener, OnRefreshLoadMoreListener {
    private lateinit var mUid: String
    private val mAdapter by lazy {
        UserCommentsAdapter().apply {
            addChildClickViewIds(R.id.iv_avatar)
            setOnItemClickListener(this@UserCommentsFragment)
            setOnItemChildClickListener(this@UserCommentsFragment)
        }
    }

    companion object {
        const val TYPE_FEEL = 0
        const val TYPE_COMMENT = 1
        fun getInstance(type: Int, uid: String): UserCommentsFragment {
            val fragment = UserCommentsFragment()
            val bundle = Bundle()
            bundle.putInt(ex.TYPE, type)
            bundle.putString(ex.UID, uid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun layoutId() = R.layout.mine_fragment_user_comments

    override fun initView(savedInstanceState: Bundle?) {
        mUid = arguments!!.getString(ex.UID)!!
        mViewModel.mUid = mUid
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
        srl_refresh.setOnRefreshLoadMoreListener(this)
        mViewModel.mCommentsLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.setEnableLoadMore(false)
            }
            mAdapter.setNewInstance(it)
        })
        mViewModel.mCommentsMoreLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            } else {
                mAdapter.addData(it)
            }
        })
        mViewModel.mCommentsFailLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
        })
        srl_refresh.autoRefresh()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val commentData = adapter.getItem(position) as CommentData?
        if (commentData == null) return
        UserHomeActivity.navTo(mContext!!, mUid)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val commentData = adapter.getItem(position) as CommentData?
        if (commentData == null) return
        UserHomeActivity.navTo(mContext!!, mUid)
        val map = HashMap<String, Any>()
        map.put(ex.TEXT_CARDS, listOf(commentData.textcard))
        toActivity(mContext!!, ex.TEXT_CARD_DETAIL, map)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        rv_content.postDelayed({
            if (mViewModel.mLastCommendId == null) return@postDelayed
            mViewModel.getCommentByUser()
        }, 300)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.mLastCommendId = null
        mViewModel.getCommentByUser()
    }
}