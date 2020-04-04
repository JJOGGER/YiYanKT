package com.jogger.module_mine.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.UserData
import com.jogger.module_mine.R

class UserWritersAdapter : BaseQuickAdapter<UserData, BaseViewHolder>(R.layout.mine_rv_user_writers_item) {
    override fun convert(holder: BaseViewHolder, item: UserData) {
        Glide.with(context)
            .load(item.smallavatar)
            .error(R.drawable.yy_default_avatar)
            .into(holder.getView(R.id.iv_avatar))
        holder.setText(R.id.tv_nickname, item.username)
            .setText(R.id.tv_subcribe, "${item.followcnt} 订阅")
            .setGone(R.id.tv_subcribe,item.followcnt==0)
    }
}