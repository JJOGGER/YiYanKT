package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.adapter.UserItemAdapter
import com.jogger.base.BaseActivity
import com.jogger.entity.UserData
import com.jogger.module_mine.R
import com.jogger.module_mine.viewmodel.UserFollowersViewModel
import com.qmuiteam.qmui.kotlin.onClick
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.mine_activity_user_followers.*

class UserFollowersActivity :
    BaseActivity<UserFollowersViewModel, com.jogger.module_mine.databinding.MineActivityUserFollowersBinding>(),
    OnItemClickListener,
    OnRefreshLoadMoreListener {

    private val mAdapter by lazy {
        UserItemAdapter().apply {
            setOnItemClickListener(this@UserFollowersActivity)
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_user_followers

    companion object {
        fun navTo(context: Context, uid: String) {
            val intent = Intent(context, UserFollowersActivity::class.java)
            intent.putExtra(ex.UID, uid)
            context.startActivity(intent)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.setTitle("订阅")
        mTopBar.addLeftBackImageButton().onClick { finish() }
        mViewModel.mUid = intent.getStringExtra(ex.UID)
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
        srl_refresh.setOnRefreshLoadMoreListener(this)
        registObserver()
        srl_refresh.autoRefresh()
    }

    private fun registObserver() {
        mViewModel.mUsersDataLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.setEnableLoadMore(false)
            }
            mAdapter.setNewInstance(it)
        })
        mViewModel.mUsersDataMoreLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.setEnableLoadMore(false)
            } else {
                mAdapter.addData(it)
            }

        })
        mViewModel.mUsersDataFailLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
        })
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        rv_content.postDelayed({
            mViewModel.getFollowers()
        }, 300)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.getFollowers()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val userData = adapter.getItem(position) as UserData?
        if (userData == null) return
        UserHomeActivity.navTo(mContext, userData.uid)
    }
}
