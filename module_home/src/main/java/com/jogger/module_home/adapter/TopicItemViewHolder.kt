package com.jogger.module_home.adapter

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jogger.base.BaseCardViewHolder
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.jogger.module_home.R
import com.qmuiteam.qmui.widget.QMUIRadiusImageView

class TopicItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {
    override fun convert(card: TextCard) {
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        if (!TextUtils.isEmpty(card.picpath)) {
            val image: ImageView?
            if (imgShow == 1 || imgShow == 11) {
                setVisible(R.id.fl_header, true)
                setVisible(R.id.iv_header, true)
                setVisible(R.id.iv_header2, false)
                image = getView(R.id.iv_header)
            } else {
                setVisible(R.id.fl_header, true)
                image = getView(R.id.iv_header2)
                setVisible(R.id.iv_header, false)
                setVisible(R.id.iv_header2, true)
                if (imgShow != 0) {
                    (image as QMUIRadiusImageView).isCircle = false
                }
            }
            getView<View>(R.id.fl_header).visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .centerCrop()
                .into(image)
        } else {
            setGone(R.id.fl_header, true)
        }
        Glide.with(mContext)
            .load(card.creator?.smallavatar)
            .into(getView(R.id.iv_avatar))
        setText(
            R.id.tv_nickname,
            card.creator?.username + " 发起了话题"
        ).setText(
            R.id.tv_book,
            if (card.creator != null && card.originbook != null && card.originbook!!.bookname != null) "[${card.originbook!!.bookname}]" else ""
        ).setText(
            R.id.tv_date,
            if (!TextUtils.isEmpty(card.datetime)) {
                val date = card.datetime!!.split("-", " ")
                val split = date[3].split(":")
                "${split[0]}:${split[1]}"
            } else ""
        )
            .setText(R.id.tv_topic_title, if (!TextUtils.isEmpty(card.title)) "[icon] ${card.title}" else "")
            .setText(R.id.tv_content, card.content)
            .setText(
                R.id.tv_from, if (!TextUtils.isEmpty(card.from)) {
                    "- " + card.from + " -"
                } else {
                    setVisible(R.id.tv_from, false)
                    ""
                }
            )
        if (existView(R.id.tv_collection) &&
            existView(R.id.tv_comment) &&
            existView(R.id.tv_like)
        ) {
            setText(R.id.tv_collection, card.collectcnt.toString())
                .setText(R.id.tv_comment, card.replycnt.toString())
                .setText(R.id.tv_like, (card.commentcnt - card.replycnt).toString())
        }
        if (existView(R.id.tv_topic_reply)) {
            setText(R.id.tv_topic_reply, "${card.replycnt}条回复")
        }
        setTypeFace(
            R.id.tv_topic_title, R.id.tv_content, R.id.tv_from, typeface = AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
        setGravity(
            R.id.tv_content,
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        )
    }
}