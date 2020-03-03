package com.jogger.module_home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import com.jogger.manager.QDSkinManager
import com.jogger.module_home.R
import com.jogger.module_home.view.widget.TextCardContainer

/**
 * Created by jogger on 2020/3/3
 * 描述：
 */
class HomePagerAdapter : PagerAdapter() {
    private val mList = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    override fun getCount(): Int {
        return mList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val cardContainer = TextCardContainer(container.context)
//        val view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_subscribe, container, false)
//
////得到AssetManager
//        val mgr = mContext!!.assets
//        //设置字体
//        val textView = view.findViewById(R.id.tv_title) as TextView
//        textView.setText("------订阅 所有:" + position)
//        if (position == 1) {
//            val tf = Typeface.createFromAsset(mgr, "fonts/fzqkyuesong.TTF");
//            textView.setTypeface(tf)
//        } else if (position == 2) {
//            val tf = Typeface.createFromAsset(mgr, "fonts/fzss.ttf");
//            textView.setTypeface(tf)
//        } else if (position == 3) {
//            val tf = Typeface.createFromAsset(mgr, "fonts/fzsx.ttf");
//            textView.setTypeface(tf)
//        } else if (position == 4) {
//            val tf = Typeface.createFromAsset(mgr, "fonts/fzzxhjt.ttf");
//            textView.setTypeface(tf)
//        } else {
//            val tf = Typeface.createFromAsset(mgr, "fonts/fzsy.ttf");
//            textView.setTypeface(tf)
//
//        }
        (cardContainer.findViewById(R.id.btn_test) as Button).setOnClickListener {
            if (position % 2 == 1) {
                QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
            } else
                QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
        }
        container.addView(cardContainer)
        return cardContainer
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}