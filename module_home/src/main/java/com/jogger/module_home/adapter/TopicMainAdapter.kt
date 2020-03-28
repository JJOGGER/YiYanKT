package com.jogger.module_home.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jogger.base.BaseCardViewHolder
import com.jogger.entity.TextCard
import com.jogger.module_home.R
import ex.sApplication

/**
 * Created by jogger on 2020/3/27
 * 描述：
 */
class TopicMainAdapter : BaseQuickAdapter<TextCard, BaseCardViewHolder>(R.layout.rv_common_topic_item, null) {
    override fun convert(holder: BaseCardViewHolder, item: TextCard) {
        holder.convert(item)
    }

    var spannable = SpannableStringBuilder("[icon] ")

    init {
        val drawable = sApplication.resources.getDrawable(R.drawable.icon_topicmark_3x)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan1 = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): BaseCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return TopicItemViewHolder(layout,parent.context)
    }
//    override fun convert(holder: BaseViewHolder, card: TextCard) {
//        val imgShow = card.type?.split("_")?.get(1)?.toInt()
//        holder.setVisible(R.id.tv_topic_title, false)
//        val image: ImageView?
//        if (imgShow == 1) {
//            image = holder.getView(R.id.iv_header)
//        } else {
//            image = holder.getView(R.id.iv_header2)
//            if (imgShow == 0) {
//                (image as QMUIRadiusImageView).isCircle = false
//            }
//        }
//        if (!TextUtils.isEmpty(card.picpath)) {
//            holder.getView<View>(R.id.fl_header).visibility = View.VISIBLE
//            image.visibility = View.VISIBLE
//            Glide.with(context)
//                .load(card.picpath)
//                .centerCrop()
//                .into(image)
//        } else {
//            holder.getView<View>(R.id.fl_header).visibility = View.GONE
//        }
//        Glide.with(context)
//            .load(card.creator?.smallavatar)
//            .into(holder.getView(R.id.iv_avatar))
//        holder.setText(
//            R.id.tv_nickname,
//            card.creator?.username + " 发起了话题"
//        ).setText(
//            R.id.tv_book,
//            if (card.creator != null && card.originbook != null && card.originbook!!.bookname != null) "[${card.originbook!!.bookname}]" else ""
//        ).setText(
//            R.id.tv_date,
//            if (!TextUtils.isEmpty(card.datetime)) {
//                val date = card.datetime!!.split("-", " ")
//                val split = date[3].split(":")
//                "${split[0]}:${split[1]}"
//            } else ""
//        )
//            .setText(R.id.tv_topic_title, if (!TextUtils.isEmpty(card.title)) spannable.append(card.title) else "")
//            .setText(R.id.tv_content, card.content)
//            .setText(
//                R.id.tv_from, if (!TextUtils.isEmpty(card.from)) {
//                    "- " + card.from + " -"
//                } else {
//                    holder.setVisible(R.id.tv_from, false)
//                    ""
//                }
//            )
//        if (holder.existView(R.id.tv_collection) &&
//            holder.existView(R.id.tv_comment) &&
//            holder.existView(R.id.tv_like)
//        ) {
//            holder.setText(R.id.tv_collection, card.collectcnt.toString())
//                .setText(R.id.tv_comment, card.replycnt.toString())
//                .setText(R.id.tv_like, (card.commentcnt - card.replycnt).toString())
//        }
//        if (holder.existView(R.id.tv_topic_reply)) {
//            holder.setText(R.id.tv_topic_reply, "${card.replycnt}条回复")
//        }
//
////        holder.setTypeFace(
////            R.id.tv_topic_title, R.id.tv_content, R.id.tv_from, typeface = AssetsManager.getTypeFaceByType(
////                card.type?.split("_")?.get(3)?.toInt() ?: 0
////            )
////        )
////        holder.setGravity(
////            R.id.tv_content,
////            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
////        )
//    }


}