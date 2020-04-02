package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.WindowModel
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.adapter.CommonCardAdapter
import com.jogger.base.BaseActivity
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.OriginBook
import com.jogger.entity.TextCard
import com.jogger.module_mine.R
import com.jogger.module_mine.viewmodel.BookListViewModel
import com.qmuiteam.qmui.kotlin.onClick
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.*
import kotlinx.android.synthetic.main.mine_activity_book_list.rv_content
import kotlinx.android.synthetic.main.mine_fragment_user_home.*

@Route(path = USER_BOOK_PAGE)
class BookListActivity : BaseActivity<BookListViewModel, ViewDataBinding>(), OnItemClickListener,
    OnRefreshLoadMoreListener, OnItemChildClickListener {

    private val mAdapter: CommonCardAdapter by lazy {
        CommonCardAdapter(null, CARD_CATEGORY.TYPE_ALL._value).apply {
            setOnItemClickListener(this@BookListActivity)
            setOnItemChildClickListener(this@BookListActivity)
        }
    }

    companion object {
        fun navTo(context: Context, title: String, uid: String) {
            context.startActivity(Intent(context, BookListActivity::class.java).apply {
                putExtra(ex.TITLE, title)
                putExtra(ex.UID, uid)
            })
        }

        fun navTo(context: Context, originBook: OriginBook?) {
            context.startActivity(Intent(context, BookListActivity::class.java).apply {
                putExtra(ex.ORIGIN_BOOK, originBook)
            })
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_book_list

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.mUid = intent.getStringExtra(ex.UID)
        if (mViewModel.mUid == null) {
            val book = intent.getParcelableExtra<OriginBook>(ORIGIN_BOOK)
            if (book == null) return
            mTopBar.setTitle(book.bookname)
            mViewModel.mBookId = book.bookid!!
        } else {
            mTopBar.setTitle(intent.getStringExtra(ex.TITLE))
        }
        mTopBar.addLeftBackImageButton().onClick { finish() }
        mTopBar.addRightImageButton(R.drawable.vmore_22_gray_3x, R.id.more_action)
            .onClick {
                val title = if (mViewModel.mInvent == 1) "查看全部卡片" else "仅看自创卡片"
                showFunctionWindow(this, WindowModel(title), listener = object : OnFunctionWindowClickListener {
                    override fun onClick(popupWindow: PopupWindow, index: Int) {
                        popupWindow.dismiss()
                        if (mViewModel.mInvent == null) {
                            mViewModel.mInvent = 1
                        } else {
                            mViewModel.mInvent = null
                        }
                        srl_refresh.autoRefresh()
                    }
                })
            }
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
        srl_refresh.setEnableFooterFollowWhenNoMoreData(true)
        srl_refresh.setOnRefreshLoadMoreListener(this)
        initObserver()
        srl_refresh.autoRefresh()
    }

    private fun initObserver() {
        mViewModel.mCardsLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            }
            mAdapter.setNewInstance(it)
        })
        mViewModel.mCardsMoreLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.isNullOrEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            } else {
                mAdapter.addData(it)
            }
        })
        mViewModel.mCardsFailLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
        })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val textCard = adapter.getItem(position) as TextCard?
        if (textCard == null) return
        val params = mapOf(
            TEXT_CARD to textCard,
            POSITION to position,
            TEXT_CARDS to adapter.data
        )
        toActivity(mContext, TEXT_CARD_DETAIL, params)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (mViewModel.mLastDateTime == null) return
        rv_content.postDelayed({
            initData()
        }, 300)
    }

    private fun initData() {
        if (mViewModel.mUid == null) {
            mViewModel.getTextCardByBook()
        } else {
            mViewModel.getTextCardByUser()
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.mLastDateTime = null
        initData()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val textCard = adapter.getItem(position) as TextCard?
        if (textCard == null) return
        when (view.id) {
            R.id.iv_avatar,
            R.id.tv_nickname
            -> {
                UserHomeActivity.navTo(mContext, textCard.creator!!.uid!!)
            }
            R.id.tv_book -> {
                navTo(mContext, textCard.originbook)
            }
        }

    }
}
