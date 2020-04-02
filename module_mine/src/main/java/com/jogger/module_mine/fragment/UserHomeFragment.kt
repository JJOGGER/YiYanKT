package com.jogger.module_mine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.jogger.base.BaseFragment
import com.jogger.entity.OriginBook
import com.jogger.entity.UserHomeData
import com.jogger.module_mine.R
import com.jogger.module_mine.activity.BookListActivity
import com.jogger.module_mine.activity.UserCommentsActivity
import com.jogger.module_mine.databinding.MineFragmentUserHomeBinding
import com.jogger.module_mine.viewmodel.UserHomeViewModel
import com.jogger.module_mine.widget.BookRecyclerView
import com.jogger.utils.MConfig
import com.qmuiteam.qmui.kotlin.onClick
import kotlinx.android.synthetic.main.mine_fragment_user_home.*
import kotlinx.android.synthetic.main.mine_rv_user_header.*

/**
 * Created by jogger on 2020/3/31
 * 描述：
 */
class UserHomeFragment :
    BaseFragment<UserHomeViewModel, MineFragmentUserHomeBinding>(), BookRecyclerView.onItemClickListener {
    private var mEditable: Boolean = true
    private var mUid: String? = null
    private var mIsMine: Boolean = false

    companion object {
        fun getInstance(userData: UserHomeData): UserHomeFragment {
            return UserHomeFragment().apply {
                val bundle = Bundle()
                bundle.putParcelable(ex.USER_DATA, userData)
                arguments = bundle
            }
        }

        fun getInstance(uid: String): UserHomeFragment {
            return UserHomeFragment().apply {
                val bundle = Bundle()
                bundle.putString(ex.UID, uid)
                arguments = bundle
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        val userData: UserHomeData? = arguments!!.get(ex.USER_DATA) as UserHomeData?
        if (userData == null) {
            mUid = arguments!!.get(ex.UID) as String
        } else {
            mUid = userData.user!!.uid!!
        }
        srl_refresh.setEnableLoadMore(false)
        srl_refresh.setOnRefreshListener({
            mViewModel.getUserInfo(mUid!!)
        })
        rv_content.setOnItemClickListener(this)
        mViewModel.mUserHomeDataLiveData.observe(this, Observer {
            initData(it)
        })
        initClick()
        if (userData == null) {
            mEditable = false
            val uid: String = arguments!!.get(ex.UID) as String
            mViewModel.getUserInfo(uid)
        } else {
            mEditable = true
            initData(userData)
        }
    }

    private fun initClick() {
        mBinding!!.userHeader.tvBooks.onClick {
            if (mBinding!!.userHeader.userData == null) return@onClick
            BookListActivity.navTo(mContext!!, mBinding!!.userHeader.userData!!.username!!, mUid!!)
        }
        mBinding!!.userHeader.tvSameFeel.onClick {
            if (mBinding!!.userHeader.userData == null) return@onClick
            UserCommentsActivity.navTo(mContext!!, mUid!!)
        }
    }

    private fun initData(userData: UserHomeData?) {
        if (userData == null) return
        mBinding!!.userHeader.userData = userData.user
        var cardCount = 0
        userData.booklist?.filter {
            it.cardcnt != 0
        }?.forEach {
            cardCount = cardCount + it.cardcnt
        }
        mIsMine = userData.user!!.uid == MConfig.getLoginResult().user!!.uid
        if (mIsMine) {
            mBinding!!.userHeader.tvSameFeel.text = "我同感的"
        } else {
            mBinding!!.userHeader.tvSameFeel.text = "同感"
        }
        mBinding!!.userHeader.cardCount = cardCount.toString()
        mBinding!!.userHeader.tvCards.onClick {
            BookListActivity.navTo(mContext!!, userData.user!!.username!!, userData.user!!.uid!!)
        }
        if (mEditable) {
            userData.booklist?.add(OriginBook())
            btn_subscrib.visibility = View.GONE
        } else {
        }
        rv_content.setData(userData.booklist)
    }

    override fun layoutId() = R.layout.mine_fragment_user_home

    override fun onAddClick() {
    }

    override fun onItemClick(book: OriginBook, position: Int) {
        if (mUid == null) return
        BookListActivity.navTo(mContext!!, book.bookname!!, mUid!!)
    }

}