package com.jogger.module_star.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.constant.CARD_CATEGORY
import com.jogger.module_star.R
import com.jogger.module_star.activity.SearchActivity
import com.qmuiteam.qmui.kotlin.onClick
import com.qmuiteam.qmui.kotlin.skin
import ex.MODULE_STAR_MAIN
import kotlinx.android.synthetic.main.star_fragment_main.*


/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
@Route(path = MODULE_STAR_MAIN)
class StarMainFragment : BaseFragment<BaseViewModel, ViewDataBinding>() {
    val mTitle = arrayListOf("热门", "发现", "话题")
    override fun layoutId(): Int = R.layout.star_fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        fl_parent.skin {
            it.background(R.attr.app_skin_common_background_1)
        }
        topbar.addRightImageButton(R.drawable.icon_serach_right_22, R.id.search_funciton)
            .onClick { navTo(SearchActivity::class.java) }
        vp_content.adapter = StarPageAdapter(childFragmentManager)
        tl_tab.setupWithViewPager(vp_content)
        vp_content.setCurrentItem(1)
    }

    override fun getBackgroundAttr(): Int {
        return R.attr.app_skin_common_background_1
    }

    inner class StarPageAdapter(f: FragmentManager) : FragmentPagerAdapter(f) {

        override fun getItem(position: Int): Fragment {
            return when {
                position == 0 -> StarItemFragment.getInstance(CARD_CATEGORY.TYPE_HOT._value)
                position == 1 -> StarFragment.getInstance(StarFragment.TYPE_FIND)
                else -> {
                    StarFragment.getInstance(StarFragment.TYPE_TOPIC)
                }
            }
        }

        override fun getCount(): Int =
            mTitle.size

        override fun getPageTitle(position: Int): CharSequence? =
            mTitle[position]
    }
}