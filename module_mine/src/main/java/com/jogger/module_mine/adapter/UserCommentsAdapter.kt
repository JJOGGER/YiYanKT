package com.jogger.module_mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.CommentData
import com.jogger.module_mine.R

/**
 * Created by jogger on 2020/4/2
 * 描述：
 */
class UserCommentsAdapter: BaseQuickAdapter<CommentData, BaseViewHolder>(R.layout.mine_rv_comment_item) {
    override fun convert(holder: BaseViewHolder, item: CommentData) {
    }
}