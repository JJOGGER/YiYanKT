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
        topbar.addRightImageButton(R.drawable.icon_serach_right_22, R.id.search_funciton)
        vp_content.adapter = StarPageAdapter(childFragmentManager)
        tl_tab.setupWithViewPager(vp_content)
        vp_content.setCurrentItem(2)
    }


    inner class StarPageAdapter(f: FragmentManager) : FragmentPagerAdapter(f) {

        override fun getItem(position: Int): Fragment {
            return when {
                position == 0 -> StarItemFragment.getInstance(CARD_CATEGORY.TYPE_HOT._value)
                position == 1 -> StarFragment.getInstance(StarFragment.TYPE_FIND)
                else -> StarFragment.getInstance(StarFragment.TYPE_TOPIC)
            }
        }

        override fun getCount(): Int =
            mTitle.size

        override fun getPageTitle(position: Int): CharSequence? =
            mTitle[position]
    }
}