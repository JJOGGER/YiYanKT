package com.jogger.module_home.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.entity.WindowModel
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jogger.base.BaseActivity
import com.jogger.entity.CommentData
import com.jogger.module_home.R
import com.jogger.module_home.adapter.CommentAdapter
import com.jogger.module_home.databinding.HomeActivityCommentBinding
import com.jogger.module_home.view.viewmodel.CommentViewModel
import com.jogger.utils.MConfig
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.util.QMUIResHelper
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import ex.*
import kotlinx.android.synthetic.main.home_activity_comment.*
import kotlinx.android.synthetic.main.home_rv_comment_header.view.*

@Route(path = COMMENT_DETAIL)
class CommentActivity : BaseActivity<CommentViewModel, HomeActivityCommentBinding>(), OnItemClickListener,
    OnRefreshLoadMoreListener, OnItemChildClickListener {
    private var mCommontData: CommentData? = null
    private var mReceiverId: String? = null
    private var mReplyStart: String = ""
    private val mIsHot by lazy {
        intent.getBooleanExtra(ex.IS_HOT, false)
    }
    val mInputManager: InputMethodManager  by lazy {
        mContext.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
    }

    companion object {
        fun navTo(context: Context, cardId: String, commentcnt: Int?, isHot: Boolean) {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra(ex.CARD_ID, cardId)
            intent.putExtra(ex.COMMENT_CNT, commentcnt)
            intent.putExtra(ex.IS_HOT, isHot)
            context.startActivity(intent)
        }
    }

    private val mAdapter: CommentAdapter by lazy {
        CommentAdapter(null).apply {
            setOnItemClickListener(this@CommentActivity)
            setOnItemChildClickListener(this@CommentActivity)
        }
    }
    private val mHeader: View by lazy {
        initHeader()
    }
    val mHeaderAdapter: CommentAdapter by lazy {
        CommentAdapter(null).apply {
            setOnItemClickListener(this@CommentActivity)
            setOnItemChildClickListener(this@CommentActivity)
        }
    }

    override fun getLayoutId() = R.layout.home_activity_comment

    override fun init(savedInstanceState: Bundle?) {
        mTopBar.setTitle(if (mIsHot) "热门" else " 感悟 ")
        mTopBar.addLeftBackImageButton().onClick { finish() }
        mViewModel.mCardId = intent.getStringExtra(ex.CARD_ID)
        rv_content.layoutManager = LinearLayoutManager(mContext)
        rv_content.adapter = mAdapter
        srl_refresh.setOnRefreshLoadMoreListener(this@CommentActivity)
        mAdapter.addHeaderView(mHeader)
        tv_send.onClick {
            var content = et_content.text.toString().trim()
            if (!TextUtils.isEmpty(content)) {
                if (!TextUtils.isEmpty(mReplyStart) && content.contains(mReplyStart)) {
                    content = content.replace(mReplyStart, "");
                    mViewModel.newComment(content, mCommontData)
                } else {
                    mViewModel.newComment(content)
                }
            }

        }
        registerObserver()


        srl_refresh.autoRefresh()
    }

    private fun registerObserver() {
        mViewModel.mCommentsLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            mAdapter.setNewInstance(it.commentlist)
            if (!it.hotlist.isNullOrEmpty()) {
                mHeader.tv_comment_count.text = "${it.count}感悟:"
                mHeader.rv_header.visibility = View.VISIBLE
                mHeader.tv_more.visibility = View.VISIBLE
                mHeaderAdapter.setNewInstance(it.hotlist)
            }
            if (it.commentlist.isNullOrEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            }
        })
        mViewModel.mCommentsMoreLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
            if (it.commentlist.isNullOrEmpty()) {
                srl_refresh.finishLoadMoreWithNoMoreData()
            } else {
                mAdapter.addData(it.commentlist!!)
                mViewModel.mLastCommentId = it.commentlist!![it.commentlist!!.size - 1].commentid
            }
        })
        mViewModel.mCommentsFailLiveData.observe(this, Observer {
            srl_refresh.closeHeaderOrFooter()
        })
        mViewModel.mNewCommentLiveData.observe(this, Observer {
            mAdapter.data.add(0, it)
            mReplyStart = ""
            et_content.text = null
            mInputManager.hideSoftInputFromWindow(et_content.windowToken, 0)
        })
        mViewModel.mNewCommentLiveData.observe(this, Observer {
            mAdapter.data.add(0, it)
            mInputManager.hideSoftInputFromWindow(et_content.windowToken, 0)
        })
        mViewModel.mDeleteCommentLiveData.observe(this, Observer {
            mAdapter.remove(mCommontData!!)
        })
    }

    private fun initHeader(): View {
        val header = LayoutInflater.from(mContext).inflate(R.layout.home_rv_comment_header, rv_content, false)
        header.findViewById<RecyclerView>(R.id.rv_header).layoutManager = object : LinearLayoutManager(mContext) {
            override fun canScrollVertically() = false
        }
        header.findViewById<RecyclerView>(R.id.rv_header).adapter = mHeaderAdapter
        if (!mIsHot) {
            val commentCnt = intent.getIntExtra(ex.COMMENT_CNT, 0)
            header.findViewById<TextView>(R.id.tv_like_count).text = "$commentCnt 同感"
            header.findViewById<TextView>(R.id.tv_more).onClick {
                navTo(mContext, mViewModel.mCardId!!, commentCnt, true)
            }
        } else {
            header.findViewById<View>(R.id.fl_top).visibility = View.GONE
        }
        return header
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mCommontData = adapter.getItem(position) as CommentData
        if (mCommontData == null) return
        if (mCommontData!!.creator?.uid === MConfig.getLoginResult().user?.uid) {

        } else {
            showWindow()
        }
    }

    private fun showWindow() {
        val isMine = MConfig.getLoginResult().user?.uid == mCommontData?.creator?.uid
        showFunctionWindow(
            this,
            if (isMine) WindowModel("删除", WindowModel.TYPE_ERROR) else WindowModel("回复"),
            listener = object : OnFunctionWindowClickListener {
                override fun onClick(popupWindow: PopupWindow, index: Int) {
                    popupWindow.dismiss()
                    if (isMine) {
                        showFunctionWindow(
                            this@CommentActivity,
                            WindowModel("确定删除", WindowModel.TYPE_ERROR),
                            listener = object : OnFunctionWindowClickListener {
                                override fun onClick(popupWindow: PopupWindow, index: Int) {
                                    popupWindow.dismiss()
                                    mViewModel.deleteComment(mCommontData!!.commentid!!)
                                }

                            })
                        return
                    }
                    mReplyStart = "回复 ${mCommontData!!.creator?.username}："
                    with(et_content) {
                        setOnKeyListener(object : View.OnKeyListener {
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (keyCode != KeyEvent.KEYCODE_DEL) return false
                                val data = text.toString()
                                if (data.startsWith(mReplyStart)) {
                                    val lastAtIndex = data.lastIndexOf(mReplyStart)
                                    val newText = data.substring(0, lastAtIndex)
                                    setText(newText)
                                    setSelection(newText.length)
                                    mReceiverId = null
                                    mReplyStart = ""
                                    return true
                                }
                                return false
                            }
                        })
                        text = SpannableStringBuilder(mReplyStart)
                            .apply {
                                setSpan(
                                    ForegroundColorSpan(
                                        QMUIResHelper.getAttrColorStateList(
                                            mContext,
                                            QMUISkinManager.defaultInstance(mContext).currentTheme,
                                            R.attr.app_skin_text_selected_color
                                        )!!.defaultColor
                                    ), 0, mReplyStart.length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                                )
                            }
                        //获取焦点并打开输入法
                        setSelection(mReplyStart.length)
                        requestFocus()
                        mReceiverId = mCommontData!!.creator?.uid
                    }
                    mInputManager.showSoftInput(et_content, 0)
                }
            })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val commentData = adapter.getItem(position) as CommentData?
        if (commentData == null) return
        when (view.id) {
            R.id.tv_name, R.id.iv_avatar -> {
                val map = HashMap<String, Any>()
                map.put(UID, commentData.creator!!.uid!!)
                toActivity(mContext, USER_HOME_PAGE, map)
            }
            R.id.tv_prise -> {
            }
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        rv_content.postDelayed({
            if (mViewModel.mLastCommentId == null) return@postDelayed
            mViewModel.getCommentsByCard(null)
        }, 300)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mViewModel.mLastCommentId = null
        mViewModel.getCommentsByCard(if (mIsHot) 1 else null)
    }
}
