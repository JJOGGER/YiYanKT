package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.adapter.CommonCardAdapter
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.module_mine.R
import ex.TEXT_CARD_DETAIL
import ex.USER_BOOK_PAGE
import ex.toActivity
import kotlinx.android.synthetic.main.mine_activity_book_list.*

@Route(path = USER_BOOK_PAGE)
class BookListActivity : BaseActivity<BaseViewModel, ViewDataBinding>(), OnItemClickListener {
    private val mAdapter: CommonCardAdapter by lazy {
        CommonCardAdapter(null, CARD_CATEGORY.TYPE_ALL._value).apply {
            setOnItemClickListener(this@BookListActivity)
        }
    }

    companion object {
        fun navTo(context: Context, title: String, uid: String) {
            context.startActivity(Intent(context, BookListActivity::class.java).apply {
                putExtra(ex.TITLE, title)
                putExtra(ex.UID, uid)
            })
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_book_list

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.setTitle(intent.getStringExtra(ex.TITLE))
        mTopBar.addLeftBackImageButton().setOnClickListener({ finish() })
        mTopBar.addRightImageButton(R.drawable.vmore_22_gray_3x, R.id.more_action)
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val textCard = adapter.getItem(position) as TextCard?
        if (textCard == null) return
        val params = mapOf(
            ex.TEXT_CARD to textCard,
            ex.POSITION to position,
            ex.TEXT_CARDS to adapter.data
        )
        toActivity(mContext, TEXT_CARD_DETAIL, params)
    }

}
