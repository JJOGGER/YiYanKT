package com.jogger.module_home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jogger.base.BaseCardViewHolder
import com.jogger.entity.TextCard
import com.jogger.module_home.R

/**
 * Created by jogger on 2020/3/27
 * 描述：
 */
class TopicMainAdapter : BaseQuickAdapter<TextCard, BaseCardViewHolder>(R.layout.rv_common_topic_item, null) {
    override fun convert(holder: BaseCardViewHolder, item: TextCard) {
        holder.convert(item)
    }
    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BaseCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return TopicItemViewHolder(layout,parent.context)
    }

}