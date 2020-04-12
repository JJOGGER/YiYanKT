package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseActivity
import com.jogger.entity.OriginBook
import com.jogger.entity.request.PublishTextCardRequest
import com.jogger.event.PublishEvent
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineActivityMyBookBinding
import com.jogger.module_mine.viewmodel.MyBookViewModel
import com.jogger.module_mine.widget.BookRecyclerView
import com.jogger.utils.MConfig
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.kotlin.skin
import kotlinx.android.synthetic.main.mine_activity_my_book.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = ex.MY_BOOK_PAGE)
class MyBookActivity : BaseActivity<MyBookViewModel, MineActivityMyBookBinding>(),
    BookRecyclerView.onItemClickListener {
    private val mRequest: PublishTextCardRequest by lazy {
        intent.getParcelableExtra<PublishTextCardRequest>(ex.PUBLISH_TEXTCARD_REQUEST)
    }
    private val mType: Int by lazy {
        intent.getIntExtra(ex.TYPE, TYPE_PUBLISH)
    }

    companion object {
        const val TYPE_PUBLISH = 0
        const val TYPE_COLLECTION = 1
        fun navTo(context: Context, request: PublishTextCardRequest) {
            val intent = Intent(context, MyBookActivity::class.java)
            intent.putExtra(ex.PUBLISH_TEXTCARD_REQUEST, request)
            intent.putExtra(ex.TYPE, TYPE_PUBLISH)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.mine_activity_my_book

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.addLeftBackImageButton().onClick { finish() }
        val booklist = MConfig.getLoginResult().booklist
        if (!booklist.isNullOrEmpty()) {
            booklist[0].isCheck = true
            mRequest.originbookid = booklist[0].bookid
        }
        booklist?.add(OriginBook())
        rv_content.setData(booklist)
        rv_content.setCheckMode(true)
        rv_content.setOnItemClickListener(this)
        when (mType) {
            TYPE_PUBLISH -> {
                mTopBar.setTitle("「选择存入文集」")
                val nextAction = mTopBar.addRightTextButton("下一步", R.id.action_next)
                nextAction.visibility = if (booklist?.size == 1) View.GONE else View.VISIBLE
                with(nextAction) {
                    skin {
                        it.textColor(R.attr.app_skin_text_selected_color)
                    }
                    onClick {
                        SelectModeActivity.navTo(mContext, mRequest)
                    }
                }
            }
            TYPE_COLLECTION -> {
                fl_bottom.visibility = View.VISIBLE
                mTopBar.setTitle("收藏")
            }
        }
    }

    override fun onItemClick(book: OriginBook, position: Int) {
        when (mType) {
            TYPE_PUBLISH -> {
                mRequest.originbookid = book.bookid
            }
            TYPE_COLLECTION -> {
                fl_bottom.visibility = View.VISIBLE
                mTopBar.setTitle("收藏")
            }
        }
    }

    override fun onAddClick() {

    }

    override fun isNeedEventBus() = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPublishEvent(event: PublishEvent) {
        if (event.mAction == PublishEvent.PUBLISH_SUCCESS) {
            finish()
        }
    }
}
