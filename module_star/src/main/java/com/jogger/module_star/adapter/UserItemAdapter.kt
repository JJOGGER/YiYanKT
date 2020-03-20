package com.jogger.module_star.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.UserData
import com.jogger.module_star.R

/**
 * Created by jogger on 2020/3/13
 * 描述：
 */
class UserItemAdapter :
    BaseQuickAdapter<UserData, BaseViewHolder>(R.layout.star_rv_user_item) {
    override fun convert(helper: BaseViewHolder, item: UserData) {
        helper.setText(R.id.tv_nickname, item.username)
        Glide.with(context)
            .load(item.smallavatar)
            .into(helper.getView(R.id.iv_avatar))
    }
}