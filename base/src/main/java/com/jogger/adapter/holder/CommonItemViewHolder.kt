package com.jogger.adapter.holder

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jogger.base.BaseCardViewHolder
import com.jogger.base.R
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.qmuiteam.qmui.widget.QMUIRadiusImageView

class CommonItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {

    override fun convert(card: TextCard) {
        setGone(R.id.text_container, false)
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView?
        if (!TextUtils.isEmpty(card.picpath)) {
            if (imgShow == 1) {
                setVisible(R.id.iv_header, true)
                setVisible(R.id.iv_header2, false)
                image = getView(R.id.iv_header)
            } else {
                image = getView(R.id.iv_header2)
                setVisible(R.id.iv_header, false)
                setVisible(R.id.iv_header2, true)
                if (imgShow == 0) {
                    (image as QMUIRadiusImageView).isCircle = false
                }
            }
            getView<View>(R.id.fl_header).visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .centerCrop()
                .into(image)
        } else {
            getView<View>(R.id.fl_header).visibility = View.GONE
        }
        Glide.with(mContext)
            .load(card.creator?.smallavatar)
            .into(getView(R.id.iv_avatar))
        setText(
            R.id.tv_nickname, card.creator?.username
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
            .setText(R.id.tv_text_title, if (!TextUtils.isEmpty(card.title)) card.title else "")
            .setText(R.id.tv_content, card.content)
            .setText(R.id.tv_text_from, if (!TextUtils.isEmpty(card.from)) "- ${card.from} -" else "")
            .setText(R.id.tv_collection, card.collectcnt.toString())
            .setText(R.id.tv_comment, card.replycnt.toString())
            .setText(R.id.tv_like, (card.commentcnt - card.replycnt).toString())
            .setText(
                R.id.tv_category, when (card.category) {
                    CARD_CATEGORY.TYPE_TEXT._value -> "#文字"
                    CARD_CATEGORY.TYPE_RECORD._value -> "#语录"
                    CARD_CATEGORY.TYPE_POETRY._value -> "#诗"
                    CARD_CATEGORY.TYPE_FILM._value -> "#电影"
                    CARD_CATEGORY.TYPE_WORD._value -> "#歌词"
                    else -> ""
                }
            )
        setTypeFace(
            R.id.tv_text_title, R.id.tv_content, R.id.tv_text_from, typeface = AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
        setTypeFace(
            R.id.tv_category, typeface = AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT1)
        )
        setGravity(
            R.id.tv_content,
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        )
    }
}