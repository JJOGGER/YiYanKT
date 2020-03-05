package com.jogger.module_star.adapter.holder

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.bumptech.glide.Glide
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.jogger.module_star.R

class CommonItemViewHolder(view: View, context: Context) : BaseCardViewHolder(view, context) {
    override fun convert(card: TextCard) {
        Glide.with(mContext)
            .load(card.picpath)
            .into(getView(R.id.iv_header))
        if (!TextUtils.isEmpty(card.title)) {
            setText(R.id.tv_text_title, card.title)
        }
        if (!TextUtils.isEmpty(card.showtime)) {
            val date = card.showtime!!.split("-", " ")
            setText(R.id.tv_date, "${date[3]}}")
        }
        if (card.creator != null && card.originbook != null) {
            setText(R.id.tv_book, "[${card.originbook!!.bookname}]")
        }
        setText(R.id.tv_collection, card.collectcnt.toString())
            .setText(R.id.tv_comment, card.replycnt.toString())
            .setText(R.id.tv_like, (card.commentcnt - card.replycnt).toString())
        setTypeFace(
            R.id.tv_text_title, R.id.tv_content, R.id.tv_text_from, typeface = AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
        isVerticalMode(R.id.tv_content, card.type?.split("_")?.get(2)?.toInt() == 0)
        setGravity(
            R.id.tv_content,
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        )

//        when (card.category) {
//            CARD_CATEGORY.TYPE_TEXT._value,
//            CARD_CATEGORY.TYPE_TOPIC._value -> {
//                mBinding.textLayout.textContainer.visibility = View.VISIBLE
//                if (!TextUtils.isEmpty(card.from))
//                    mBinding.textLayout.tvTextFrom.text = "- ${card.from} -"
//                if (card.category == CARD_CATEGORY.TYPE_TEXT._value) {
//                    mBinding.commonBottomContainer.visibility = View.VISIBLE
//                    mBinding.textLayout.tvContent.text = card.content
//                    mBinding.tvCategory.text = "#文字"
//                } else {
//                    val drawable = resources.getDrawable(R.drawable.icon_topicmark_3x)
//                    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
//                    mBinding.textLayout.tvTextTitle.setCompoundDrawables(drawable, null, null, null)
//                    mBinding.textLayout.tvTextTitle.compoundDrawablePadding = 10
//                    mBinding.topicBottom.topicBottomContainer.visibility = View.VISIBLE
//                    mBinding.topicBottom.tvTopicReply.text = "${card.replycnt}条回复"
//                    mBinding.topicBottom.tvTopicCreated.text = "${card.creator?.username}发起了话题"
//                }
//
//            }
//            CARD_CATEGORY.TYPE_POETRY._value -> {
//                mBinding.tvCategory.text = "#诗"
//            }
//            CARD_CATEGORY.TYPE_MUSIC._value -> {
//                mBinding.musicLayout.musicContainer.visibility = View.VISIBLE
//                mBinding.commonBottomContainer.visibility = View.VISIBLE
//                if (!TextUtils.isEmpty(card.title))
//                    mBinding.musicLayout.tvMusicTitle.text = card.title
//                if (!TextUtils.isEmpty(card.from))
//                    mBinding.musicLayout.tvMusicFrom.text = "- ${card.from} -"
//            }
//        }
    }
}