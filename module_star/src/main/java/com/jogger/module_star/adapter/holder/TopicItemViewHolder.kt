package com.jogger.module_star.adapter.holder

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.jogger.module_star.R
import com.jogger.utils.LogUtils
import com.qmuiteam.qmui.widget.QMUIRadiusImageView

class TopicItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {
    var spannable = SpannableStringBuilder("[icon] ")

    init {
        val drawable = context.resources.getDrawable(R.drawable.icon_topicmark_3x)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan1 = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    override fun convert(card: TextCard) {
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView?
        if (imgShow == 1) {
            image = getView(R.id.iv_header)
        } else {
            image = getView(R.id.iv_header2)
            if (imgShow == 0) {
                (image as QMUIRadiusImageView).isCircle = false
            }
        }
        if (!TextUtils.isEmpty(card.picpath)) {
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
            .setText(R.id.tv_text_title, if (!TextUtils.isEmpty(card.title)) spannable.append(card.title) else "")
            .setText(R.id.tv_content, card.content)
            .setText(R.id.tv_topic_reply, "${card.replycnt}条回复")
        setTypeFace(
            R.id.tv_text_title, R.id.tv_content, typeface = AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
        setGravity(
            R.id.tv_content,
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        )
        getView<View>(R.id.layout_item)
            .setOnClickListener({
                LogUtils.e("--------card$card")
            })
    }
}