package com.jogger.module_mine.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.adapter.CommonCardAdapter
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.module_mine.R
import kotlinx.android.synthetic.main.mine_fragment_mine.*

class BookListActivity : BaseActivity<BaseViewModel, ViewDataBinding>(), OnItemClickListener {
    private val mAdapter: CommonCardAdapter by lazy {
        CommonCardAdapter(null, CARD_CATEGORY.TYPE_ALL._value).apply {
            setOnItemClickListener(this@BookListActivity)
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_book_list

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.addLeftBackImageButton().setOnClickListener({ finish() })
        mTopBar.addRightImageButton(R.drawable.vmore_22_gray_3x, R.id.more_action)
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }

}
