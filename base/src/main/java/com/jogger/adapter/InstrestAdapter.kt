package com.jogger.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ImageSpan
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.base.R
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager

class InstrestAdapter(data: MutableList<TextCard>?) :
    BaseQuickAdapter<TextCard, BaseViewHolder>(R.layout.star_rv_instrest_item_item, data) {

    override fun convert(holder: BaseViewHolder, item: TextCard) {
        val text = "[icon] " + item.content
        val spannable = SpannableStringBuilder(text)
        val drawable = context.resources.getDrawable(R.drawable.text_quote_3x)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan1 = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        Glide.with(context)
            .load(item.creator?.smallavatar)
            .into(holder.getView(R.id.iv_avatar))
        holder.setText(R.id.tv_nickname, item.creator?.username)
            .setText(
                R.id.tv_subcribe,
                if (!TextUtils.isEmpty(item.showtime)) {
                    val date = item.showtime!!.split("-", " ")
                    "${date[3]}}"
                } else ""
            )
            .setText(R.id.tv_content, spannable)
        holder.getView<TextView>(R.id.tv_content)
            .typeface = AssetsManager.getTypeFaceByType(
            item.type?.split("_")?.get(3)?.toInt() ?: 0
        )
    }
}