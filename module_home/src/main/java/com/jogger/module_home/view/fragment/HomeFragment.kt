package com.jogger.module_home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.module_home.R
import com.jogger.widget.YiYaHeader
import ex.MODULE_HOME_MAIN
import kotlinx.android.synthetic.main.home_fragment_home.*


/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
@Route(path = MODULE_HOME_MAIN)
class HomeFragment : BaseFragment<BaseViewModel, ViewDataBinding>() {
    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vp_content.adapter = MyPagerAdapter()
//        vp_content.setPageTransformer(true,MyZoomOutPageTransformer())
        srl_refresh.setRefreshHeader(YiYaHeader(mContext!!))
    }

    override fun layoutId(): Int = com.jogger.module_home.R.layout.home_fragment_home

    inner class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val mList = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        override fun getItem(position: Int): Fragment {
            return SubscribeFragment.newInstance(position)
        }

        override fun getCount(): Int =
            mList.size

    }
    inner class MyPagerAdapter() : PagerAdapter() {
        private val mList = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        override fun getCount(): Int {
            return  mList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_subscribe, container, false)
            (view.findViewById(R.id.tv_title) as TextView).setText("------pos:"+position)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
    inner class MyZoomOutPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            if (position <= 1) {

                val scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE)

                page.scaleX = scaleFactor

                if (position > 0) {
                    page.translationX = -scaleFactor * 2
                } else if (position < 0) {
                    page.translationX = scaleFactor * 2
                }
                page.scaleY = scaleFactor
            } else {

                page.scaleX = MIN_SCALE
                page.scaleY = MIN_SCALE
            }
        }

        //自由控制缩放比例
        private val MAX_SCALE = 1f
        private val MIN_SCALE = 0.8f//0.85f
    }
}