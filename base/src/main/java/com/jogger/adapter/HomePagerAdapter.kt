package com.jogger.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.jogger.entity.TextCard
import com.jogger.widget.TextCardContainer
import com.qmuiteam.qmui.kotlin.onClick
import ex.toActivity

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
        cardContainer.onClick {
            val params = mapOf(
                ex.TEXT_CARD to mDatas.get(position),
                ex.POSITION to position,
                ex.TEXT_CARDS to mDatas
            )
            toActivity(container.context, ex.TEXT_CARD_DETAIL, params)
        }
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