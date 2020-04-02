package com.jogger.module_mine.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.module_mine.R
import com.jogger.module_mine.databinding.MineActivityUserCommentsBinding
import com.jogger.module_mine.fragment.UserCommentsFragment
import com.jogger.utils.MConfig
import com.jogger.widget.CommonPage2Adapter
import com.qmuiteam.qmui.widget.tab.QMUITab
import com.qmuiteam.qmui.widget.tab.QMUITabIndicator
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import com.qmuiteam.qmui.widget.tab.TabMediator
import kotlinx.android.synthetic.main.mine_activity_user_comments.*

class UserCommentsActivity : BaseActivity<BaseViewModel, MineActivityUserCommentsBinding>() {
    val mIsMine: Boolean by lazy {
        mUid == MConfig.getLoginResult().user!!.uid
    }
    val mUid: String by lazy {
        intent.getStringExtra(ex.UID)
    }
    private val mAdapter by lazy {
        val fragments = arrayListOf<UserCommentsFragment>().apply {
            add(UserCommentsFragment.getInstance(UserCommentsFragment.TYPE_FEEL, mUid))
            if (mIsMine)
                add(UserCommentsFragment.getInstance(UserCommentsFragment.TYPE_COMMENT, mUid))
        }
        CommonPage2Adapter(this, fragments)
    }

    override fun getLayoutId() = R.layout.mine_activity_user_comments

    companion object {
        fun navTo(context: Context, uid: String) {
            val intent = Intent(context, UserCommentsActivity::class.java)
            intent.putExtra(ex.UID, uid)
            context.startActivity(intent)
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        vp_content.adapter = mAdapter
        if (mIsMine) {
            val fragment = QMUITabSegment(mContext).apply {
                val tabBuilder = tabBuilder()
                addTab(
                    tabBuilder.setText("同感")
                        .build(mContext)
                )
                addTab(tabBuilder.setText("评论").build(mContext))
                setIndicator(
                    QMUITabIndicator(
                        resources.getDimensionPixelOffset(R.dimen.dp_10),
                        resources.getDimensionPixelSize(com.qmuiteam.qmui.R.dimen.qmui_tab_segment_indicator_height),
                        false, false
                    )
                )
                mode = QMUITabSegment.MODE_SCROLLABLE
                setItemSpaceInScrollMode(resources.getDimensionPixelOffset(R.dimen.dp_26))
            }
            mTopBar.setCenterView(fragment)
            TabMediator(mContext,
                fragment
                , vp_content, true, object : TabMediator.TabConfigurationStrategy {
                    override fun onConfigureTab(tab: QMUITab, position: Int) {
                        tab.text = if (position == 0) "所有" else "热门"
                    }
                }).attach()
        } else {
            mTopBar.setTitle("同感")
        }
    }

}
