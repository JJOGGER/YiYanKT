package com.jogger.module_home.adapter

import android.text.TextUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.CommentData
import com.jogger.module_home.R

/**
 * Created by jogger on 2020/3/30
 * 描述：
 */
class CommentAdapter(datas: MutableList<CommentData>?) :
    BaseQuickAdapter<CommentData, BaseViewHolder>(R.layout.home_rv_comment_item, datas) {

    override fun convert(holder: BaseViewHolder, item: CommentData) {
        Glide.with(context)
            .load(item.creator?.smallavatar)
            .error(R.drawable.yy_default_avatar)
            .into(holder.getView(R.id.iv_avatar))
        holder.setText(R.id.tv_nickname, item.creator?.username)
            .setText(
                R.id.tv_date, if (!TextUtils.isEmpty(item.datetime)) {
                    val date = item.datetime!!.split("-", " ")
                    "${date[1]}-${date[2]}"
                } else ""
            )
            .setText(R.id.tv_prise, item.likecnt.toString())
            .setText(R.id.tv_content, item.content)
            .setVisible(R.id.tv_reply_title, item.receiver != null)
            .setVisible(
                R.id.tv_reply_name, if (item.receiver != null) {
                    holder.setText(R.id.tv_reply_name, item.receiver!!.username)
                    true
                } else {
                    false
                }
            )

    }
}