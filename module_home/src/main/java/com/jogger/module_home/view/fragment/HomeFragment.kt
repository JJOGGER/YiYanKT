package com.jogger.module_home.view.fragment

import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jogger.base.BaseFragment
import com.jogger.base.BaseViewModel
import com.jogger.manager.QDSkinManager
import com.jogger.module_home.R
import com.jogger.widget.YiYaHeader
import ex.MODULE_HOME_MAIN
import ex.showToast
import kotlinx.android.synthetic.main.home_fragment_home.*
import kotlinx.android.synthetic.main.home_fragment_subscribe.*


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
            return mList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_subscribe, container, false)

//得到AssetManager
            val mgr=mContext!!.assets
                //设置字体
            val textView = view.findViewById(R.id.tv_title) as TextView
            textView.setText("------pos:" + position)
            if (position==1){
                val tf=Typeface.createFromAsset(mgr, "fonts/fzqkyuesong.TTF");
                textView.setTypeface(tf)
            }else if (position==2){
                val tf=Typeface.createFromAsset(mgr, "fonts/fzss.ttf");
                textView.setTypeface(tf)

            }else if (position==3){
                val tf=Typeface.createFromAsset(mgr, "fonts/fzsx.ttf");
                textView.setTypeface(tf)
            }else if (position==4){
                val tf=Typeface.createFromAsset(mgr, "fonts/fzzxhjt.ttf");
                textView.setTypeface(tf)
            }else{
                val tf=Typeface.createFromAsset(mgr, "fonts/fzsy.ttf");
                textView.setTypeface(tf)

            }
            (view.findViewById(R.id.btn_test) as Button).setOnClickListener {
                if (position % 2 == 1) {
                    QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
                } else
                    QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}