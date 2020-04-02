package com.jogger.module_home.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.widget.NestedScrollView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseActivity
import com.jogger.base.BaseViewModel
import com.jogger.entity.TextCard
import com.jogger.module_home.R
import com.jogger.module_home.databinding.HomeActivityTopicMainBinding
import com.jogger.module_home.view.fragment.TopicMainFragment
import com.jogger.widget.CommonPage2Adapter
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.widget.tab.QMUITab
import com.qmuiteam.qmui.widget.tab.TabMediator
import ex.TEXT_CARD
import ex.TOPIC_MIAN_DETAIL
import kotlinx.android.synthetic.main.home_activity_topic_main.*

@Route(path = TOPIC_MIAN_DETAIL)
class TopicMainActivity : BaseActivity<BaseViewModel, HomeActivityTopicMainBinding>() {
    private var mTabPosition: Float? = null
    lateinit var mAdapter: CommonPage2Adapter
    override fun getLayoutId() = R.layout.home_activity_topic_main
    override fun init(savedInstanceState: Bundle?) {
        mTopBar.addLeftBackImageButton().onClick { finish() }
        mTopBar.addRightImageButton(R.drawable.vmore_22_gray_3x, R.id.ibtn_more)
            .onClick {}
        val textCard = intent.getParcelableExtra<TextCard>(TEXT_CARD)
        mBinding!!.textCard = textCard
        val fragments = arrayListOf<TopicMainFragment>().apply {
            add(TopicMainFragment.getInstance(TopicMainFragment.TYPE_ALL, textCard.textcardid!!))
            add(TopicMainFragment.getInstance(TopicMainFragment.TYPE_HOT, textCard.textcardid!!))
        }
        mAdapter = CommonPage2Adapter(this, fragments)
        vp_content.adapter = mAdapter

        initTab()
        nsv_root.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (mTabPosition == null) return
                if (scrollY >= mTabPosition!!) {
                    if (tl_tab2.visibility == View.GONE)
                        tl_tab2.visibility = View.VISIBLE
                    nsv_root.setNeedScroll(false)
                    if (TextUtils.isEmpty(mTopBar.title))
                        mTopBar.setTitle("[icon] ${textCard.title}")
                } else {
                    if (tl_tab2.visibility == View.VISIBLE)
                        tl_tab2.visibility = View.GONE
                    nsv_root.setNeedScroll(true)
                    if (!TextUtils.isEmpty(mTopBar.title))
                        mTopBar.setTitle("")
                }
            }
        })
    }

    private fun initTab() {
        tl_tab.setColorAttr(R.attr.qmui_skin_topic_tab_normal_color, R.attr.qmui_skin_topic_tab_selected_color)
        tl_tab.setDefaultSelectedIndicatorColorAttr(R.attr.qmui_skin_topic_tab_selected_color)
        TabMediator(this, tl_tab, vp_content, true, object : TabMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: QMUITab, position: Int) {
                tab.text = if (position == 0) "所有" else "热门"
            }
        }).attach()
        tl_tab2.setColorAttr(R.attr.qmui_skin_topic_tab_normal_color, R.attr.qmui_skin_topic_tab_selected_color)
        tl_tab2.setDefaultSelectedIndicatorColorAttr(R.attr.qmui_skin_topic_tab_selected_color)
        TabMediator(this, tl_tab2, vp_content, true, object : TabMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: QMUITab, position: Int) {
                tab.text = if (position == 0) "所有" else "热门"
            }
        }).attach()
        tl_tab.viewTreeObserver.addOnGlobalLayoutListener({
            mTabPosition = tl_tab.y
            tl_tab.viewTreeObserver.removeOnDrawListener { }
        })
    }
}
