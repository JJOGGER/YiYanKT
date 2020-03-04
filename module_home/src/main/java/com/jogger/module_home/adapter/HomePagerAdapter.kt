package com.jogger.module_home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.jogger.entity.TextCard
import com.jogger.module_home.view.widget.TextCardContainer
import com.jogger.widget.HomeViewPager

/**
 * Created by jogger on 2020/3/3
 * 描述：
 */
class HomePagerAdapter(vpContent: HomeViewPager) : PagerAdapter() {
    private val mDatas = arrayListOf<TextCard>()
    private val vpContent: HomeViewPager = vpContent
    private val padding = vpContent.paddingStart
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val cardContainer = TextCardContainer(container.context)
//        if (position == 0) {
//            LogUtils.e("-------pos=0")
//            vpContent.setPadding(0, 0, padding, 0)
//        } else {
//            LogUtils.e("-------pos=$position")
//            vpContent.setPadding(padding, 0, padding, 0)
//        }
        cardContainer.setData(mDatas.get(position))
//        (cardContainer.findViewById(R.id.btn_test) as Button).setOnClickListener {
//            if (position % 2 == 1) {
//                QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
//            } else
//                QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
//        }
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(cardContainer, params)
        return cardContainer
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun setDatas(datas: List<TextCard>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    fun addDatas(datas: List<TextCard>) {
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}