package com.jogger.module_mine.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.CommentData
import com.jogger.module_mine.R

/**
 * Created by jogger on 2020/4/2
 * 描述：
 */
class UserCommentsAdapter : BaseQuickAdapter<CommentData, BaseViewHolder>(R.layout.mine_rv_comment_item) {
    override fun convert(holder: BaseViewHolder, item: CommentData) {
        holder.setText(R.id.tv_title, item.textcard?.content)
            .setText(R.id.tv_content, item.content)
        Glide.with(context)
            .load(item.creator?.smallavatar)
            .into(holder.getView(R.id.iv_avatar))
    }
}