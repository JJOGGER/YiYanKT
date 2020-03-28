package com.jogger.module_star.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.jogger.base.BaseCardViewHolder
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.module_star.R
import com.jogger.module_star.adapter.holder.CommonItemViewHolder
import com.jogger.module_star.adapter.holder.InstrestItemViewHolder
import com.jogger.module_star.adapter.holder.TopicItemViewHolder

class StarAdapter(data: MutableList<TextCard>?, type: Int) :
    BaseMultiItemQuickAdapter<TextCard, BaseCardViewHolder>(data) {
    private val mType = type
    private val mItemTypes = arrayListOf(
        CARD_CATEGORY.TYPE_TEXT,
        CARD_CATEGORY.TYPE_POETRY,
        CARD_CATEGORY.TYPE_FILM,
        CARD_CATEGORY.TYPE_RECORD,
        CARD_CATEGORY.TYPE_WORD,
        CARD_CATEGORY.TYPE_MUSIC,
        CARD_CATEGORY.TYPE_TOPIC,
        CARD_CATEGORY.TYPE_HOT_CARD
    )

    init {
        for (type in mItemTypes) {
            when (type) {
                CARD_CATEGORY.TYPE_TEXT,
                CARD_CATEGORY.TYPE_POETRY,
                CARD_CATEGORY.TYPE_FILM,
                CARD_CATEGORY.TYPE_WORD ->
                    addItemType(type._value, R.layout.star_rv_common_item)
                CARD_CATEGORY.TYPE_HOT_CARD -> addItemType(type._value, R.layout.star_rv_instrest_item)
                CARD_CATEGORY.TYPE_TOPIC -> if (mType == CARD_CATEGORY.TYPE_TOPIC._value) addItemType(
                    type._value,
                    R.layout.rv_common_topic_item
                ) else addItemType(
                    type._value,
                    R.layout.star_rv_topic_item
                )
                else -> addItemType(type._value, R.layout.star_rv_common_item)
            }
        }
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BaseCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return when (layoutResId) {
            R.layout.star_rv_common_item -> {
                CommonItemViewHolder(layout, parent.context)
            }
            R.layout.star_rv_instrest_item -> {
                InstrestItemViewHolder(layout, parent.context)
            }
            R.layout.rv_common_topic_item, R.layout.star_rv_topic_item -> {
                TopicItemViewHolder(layout, parent.context)
            }
            else -> super.createBaseViewHolder(parent, layoutResId)
        }
    }

    override fun convert(helper: BaseCardViewHolder, item: TextCard) {
        helper.convert(item);
    }
}