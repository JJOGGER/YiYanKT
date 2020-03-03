package com.jogger.module_home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager.widget.PagerAdapter
import com.jogger.entity.TextCard
import com.jogger.manager.QDSkinManager
import com.jogger.module_home.R
import com.jogger.module_home.view.widget.TextCardContainer

/**
 * Created by jogger on 2020/3/3
 * 描述：
 */
class HomePagerAdapter : PagerAdapter() {
    private val mDatas = arrayListOf<TextCard>()
    override fun getCount(): Int {
        return mDatas.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val cardContainer = TextCardContainer(container.context)
        cardContainer.setData(mDatas.get(position))
//        (cardContainer.findViewById(R.id.btn_test) as Button).setOnClickListener {
//            if (position % 2 == 1) {
//                QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
//            } else
//                QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
//        }
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(cardContainer,params)
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

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}