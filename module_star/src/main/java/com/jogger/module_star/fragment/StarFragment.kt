package com.jogger.module_star.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jogger.base.BaseFragment
import com.jogger.constant.CARD_CATEGORY
import com.jogger.module_star.viewmodel.StarViewModel
import com.qmuiteam.qmui.widget.tab.QMUITabSegment
import ex.INDEX
import kotlinx.android.synthetic.main.star_fragment.*


/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class StarFragment : BaseFragment<StarViewModel, ViewDataBinding>() {
    var mType: Int = TYPE_FIND
    val mTabTypes = arrayListOf(
        CARD_CATEGORY.TYPE_ALL,
        CARD_CATEGORY.TYPE_ORIGIN,
        CARD_CATEGORY.TYPE_TEXT,
        CARD_CATEGORY.TYPE_POETRY,
        CARD_CATEGORY.TYPE_FILM,
        CARD_CATEGORY.TYPE_RECORD,
        CARD_CATEGORY.TYPE_WORD,
        CARD_CATEGORY.TYPE_MUSIC
    )
    val mFinderTitles = arrayListOf(
        "所有",
        "原创",
        "文字",
        "诗",
        "电影",
        "语录",
        "歌词",
        "音乐"
    )
    val mTopicTitles = arrayListOf(
        "发现",
        "我参与的"
    )
    private val mFragments = arrayListOf<BaseFragment<*, *>>()

    companion object {
        const val TYPE_FIND = 1
        const val TYPE_TOPIC = 2
        fun getInstance(type: Int): StarFragment {
            val fragment = StarFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mType = arguments?.getInt(INDEX)!!
        if (mType == TYPE_FIND) {
            for (i in mTabTypes) {
                mFragments.add(StarItemFragment.getInstance(i._value))
            }
        } else {
            val fragment = StarItemFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, CARD_CATEGORY.TYPE_TOPIC_FIND._value)
            fragment.arguments = bundle
            mFragments.add(fragment)
            mFragments.add(StarItemFragment.getInstance(CARD_CATEGORY.TYPE_TOPIC._value))
            tab.mode = QMUITabSegment.MODE_FIXED
        }
        val starPageAdapter = StarPageAdapter(childFragmentManager)
        vp_item.adapter = starPageAdapter
        vp_item.canScrollHorizontally(0)
//        tab.setTypeFace(
//            AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT1)
//        )
        tab.setupWithViewPager(vp_item, true)
        tab.setOnTabClickListener(object : QMUITabSegment.OnTabClickListener {
            override fun onTabClick(index: Int) {
                if (tab.selectedIndex == index) {
                    starPageAdapter.getFragment(index).lazyLoadData()
                }
            }
        })
    }

    override fun layoutId(): Int = com.jogger.module_star.R.layout.star_fragment

    inner class StarPageAdapter(f: FragmentManager) : FragmentStatePagerAdapter(f) {
        private val mPageReferenceMap = hashMapOf<Int, BaseFragment<*, *>>()
        override fun getItem(position: Int): Fragment {
            mPageReferenceMap.put(position, mFragments[position])
            return mFragments[position]
        }


        override fun getCount(): Int =
            if (mType == TYPE_FIND) mFinderTitles.size else mTopicTitles.size

        override fun getPageTitle(position: Int): CharSequence? =
            if (mType == TYPE_FIND) mFinderTitles[position] else mTopicTitles[position]

        fun getFragment(key: Int): BaseFragment<*, *> {
            return mPageReferenceMap.get(key)!!
        }
    }
}