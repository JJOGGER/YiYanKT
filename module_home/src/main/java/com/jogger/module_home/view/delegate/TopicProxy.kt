package com.jogger.module_home.view.delegate

import android.app.Activity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.View
import com.bumptech.glide.Glide
import com.jogger.base.R
import com.jogger.module_home.databinding.HomeDetailTopicViewBinding

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TopicProxy(binding: HomeDetailTopicViewBinding, context: Activity) :
    BaseProxy<HomeDetailTopicViewBinding>(binding, context) {
    var spannable = SpannableStringBuilder("[icon] ")
    override fun initView() {
        mBinding.bottomAction.ibtnMore.visibility = View.GONE
        mBinding.bottomAction.ibtnShare.visibility = View.GONE
        mBinding.bottomAction.tvCollection.visibility = View.GONE
        mBinding.bottomAction.tvComment.visibility = View.GONE
        mBinding.bottomAction.tvLike.visibility = View.GONE

        val card = mBinding.textCard!!
        if (!TextUtils.isEmpty(card.picpath)) {
            mBinding.ivHeader.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .into(mBinding.ivHeader)
        } else {
            mBinding.ivHeader.visibility = View.GONE
        }
        val drawable = mContext.resources.getDrawable(R.drawable.icon_topicmark_3x)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan1 = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannable.append(card.title)
        mBinding.tvTextTitle.setText(spannable)
        if (!TextUtils.isEmpty(card.showtime)) {
            val date = card.showtime!!.split("-", " ")
            mBinding.tvDate.text = "${date[0]}\n/\n${date[1]}\n/\n${date[2]}"
        }
//        if (card.creator != null && card.originbook != null)
//            mBinding.tvCreated.text = "${card.creator!!.username} 创建于 [${card.originbook!!.bookname}]"
//        mBinding.commonBottom.tvCollection.text = "${card.collectcnt}"
//        mBinding.commonBottom.tvComment.text = "${card.replycnt}"
//        mBinding.commonBottom.tvLike.text = "${(card.commentcnt - card.replycnt)}"
//        mBinding.tvCreated.typeface = AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT4)
//        mBinding.tvCategory.typeface = mBinding.tvCreated.typeface
//        mBinding.topicBottom.tvTopicReply.typeface = mBinding.tvCreated.typeface
//        mBinding.topicBottom.tvTopicCreated.typeface = mBinding.tvCreated.typeface
//        mBinding.textLayout.tvTextTitle.typeface =
//            AssetsManager.getTypeFaceByType(
//                card.type?.split("_")?.get(3)?.toInt() ?: 0
//            )
//        mBinding.textLayout.tvContent.typeface = mBinding.textLayout.tvTextTitle.typeface
//        mBinding.textLayout.tvTextFrom.typeface = mBinding.textLayout.tvTextTitle.typeface
//        mBinding.musicLayout.tvMusicTitle.typeface = mBinding.textLayout.tvTextTitle.typeface
//        mBinding.musicLayout.tvMusicFrom.typeface = mBinding.textLayout.tvTextTitle.typeface
////        mBinding.textLayout.tvContent.isVerticalMode = card.type?.split("_")?.get(2)?.toInt() == 0
//        mBinding.textLayout.tvContent.gravity =
//            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
//        setOnClickListener({
//            LogUtils.e("--------card$card")
//        })
    }

}