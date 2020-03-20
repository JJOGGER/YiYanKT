package com.jogger.module_star.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jogger.adapter.HomePagerAdapter
import com.jogger.base.BaseActivity
import com.jogger.entity.TextCard
import com.jogger.entity.UserData
import com.jogger.module_star.R
import com.jogger.module_star.adapter.UserItemAdapter
import com.jogger.module_star.databinding.StarActivitySearchBinding
import com.jogger.module_star.viewmodel.SearchViewModel
import ex.showToast
import kotlinx.android.synthetic.main.star_activity_search.*

class SearchActivity : BaseActivity<SearchViewModel, StarActivitySearchBinding>() {
    private val INDEX_TEXT = 0
    private val INDEX_USER = 1
    private var mCurrentIndex = INDEX_TEXT
    private lateinit var mTextAdapter: HomePagerAdapter
    private lateinit var mUserAdapter: UserItemAdapter
    override fun getLayoutId(): Int = R.layout.star_activity_search

    override fun init(savedInstanceState: Bundle?) {
        tab_text.isSelected = true
        et_search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    with(et_search.text.toString().trim()) {
                        if (TextUtils.isEmpty(this)) {
                            showToast("请输入搜索文字或用户名")
                            return true
                        }
                        if (mCurrentIndex == INDEX_TEXT) {
                            mViewModel.mMoreExtra = null
                            mViewModel.mTextIndex = null
                            mViewModel.searchTextCards(this)
                        } else {
                            mViewModel.mUserIndex = null
                            mViewModel.searchUsers(this)
                        }
                    }
                    et_search.clearFocus()
                    return true
                }
                return false
            }
        })
        tab_text.setOnClickListener({
            tab_user.isSelected = false
            tab_text.isSelected = true
            mCurrentIndex = INDEX_TEXT
            srl_text_refresh.visibility = View.VISIBLE
            srl_user_refresh.visibility = View.GONE
        })
        tab_user.setOnClickListener({
            tab_user.isSelected = true
            tab_text.isSelected = false
            mCurrentIndex = INDEX_USER
            srl_text_refresh.visibility = View.GONE
            srl_user_refresh.visibility = View.VISIBLE
        })
        tv_cancel.setOnClickListener { finish() }
        mTextAdapter = HomePagerAdapter()
        mUserAdapter = UserItemAdapter()
        srl_text_refresh.setEnableRefresh(false)
        srl_user_refresh.setEnableRefresh(false)
//        srl_text_refresh.setRefreshFooter(ClassicsFooter(mContext))
        srl_text_refresh.setOnLoadMoreListener {
            mViewModel.searchTextCards(et_search.text.toString().trim())
        }
//        srl_user_refresh.setRefreshFooter(ClassicsFooter(mContext))
        srl_user_refresh.setOnLoadMoreListener {
            mViewModel.searchUsers(et_search.text.toString().trim())
        }
        mBinding!!.stubUserList.rvContent.layoutManager = LinearLayoutManager(mContext)
        mBinding!!.stubUserList.rvContent.adapter = mUserAdapter

        mViewModel.mSearchTextCardLiveData.observe(this, Observer { handleTextSearchResult(it) })
        mViewModel.mSearchUsersLiveData.observe(this, Observer { handleUserSearchResult(it) })
    }

    override fun hasTitleAction(): Boolean = false
    private fun handleTextSearchResult(it: MutableList<TextCard>?) {
        srl_text_refresh.closeHeaderOrFooter()
        if (it == null || it.isEmpty()) {
            if (mViewModel.mTextIndex == null) {
                srl_text_refresh.finishLoadMoreWithNoMoreData()
            }
        } else {
            if (mViewModel.mTextIndex == null) {
                mTextAdapter.setDatas(it)
            } else {
                mTextAdapter.addDatas(it)
            }
            mViewModel.mTextIndex = mTextAdapter.count
            mBinding!!.stubTextList.vpContent.adapter = mTextAdapter
        }
    }

    private fun handleUserSearchResult(it: MutableList<UserData>?) {
        srl_user_refresh.closeHeaderOrFooter()
        if (it == null || it.isEmpty()) {
            if (mViewModel.mUserIndex == null) {
                srl_text_refresh.finishLoadMoreWithNoMoreData()
            }
        } else {
            if (mViewModel.mUserIndex == null) {
                mUserAdapter.setNewData(it)
            } else {
                mUserAdapter.addData(it)
            }
            mViewModel.mUserIndex = mUserAdapter.data.size
        }
    }

}
