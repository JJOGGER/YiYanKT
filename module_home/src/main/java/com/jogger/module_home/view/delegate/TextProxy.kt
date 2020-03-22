package com.jogger.module_home.view.delegate

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jogger.module_home.databinding.HomeDetailTextViewBinding

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextProxy(binding: HomeDetailTextViewBinding,context:Context) : BaseProxy<HomeDetailTextViewBinding>(binding,context) {

    override fun initView() {
        val card = mBinding.textCard!!
        val imgShow =card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView
        if (imgShow == 1) {
            image = mBinding.ivHeader
        } else {
            image = mBinding.ivHeader2
            if (imgShow == 0) {
                image.isCircle = false
            }
        }
        if (!TextUtils.isEmpty(card.picpath)) {
            mBinding.flHeader.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .into(image)
        } else {
            mBinding.flHeader.visibility = View.GONE
        }
//        if (!TextUtils.isEmpty(card.title)) {
//            mBinding.textLayout.tvTextTitle.text = card.title
//        }
        if (!TextUtils.isEmpty(card.showtime)) {
//            mBinding.tabView.visibility = View.VISIBLE
//            val date = card.showtime!!.split("-", " ")
//            mBinding.tvDate.text = "${date[0]}\n/\n${date[1]}\n/\n${date[2]}"
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
//        when (card.category) {
//            CARD_CATEGORY.TYPE_TEXT._value,
//            CARD_CATEGORY.TYPE_TOPIC._value,
//            CARD_CATEGORY.TYPE_POETRY._value -> {
//                mBinding.textLayout.textContainer.visibility = View.VISIBLE
//                if (!TextUtils.isEmpty(card.from))
//                    mBinding.textLayout.tvTextFrom.text = "- ${card.from} -"
//                if (card.category == CARD_CATEGORY.TYPE_TEXT._value || card.category == CARD_CATEGORY.TYPE_POETRY._value) {
//                    mBinding.commonBottomContainer.visibility = View.VISIBLE
//                    mBinding.textLayout.tvContent.text = card.content
//                    mBinding.tvCategory.text = "#文字"
//                } else {
//                    spannable.append(card.title)
//                    mBinding.textLayout.tvTextTitle.setText(spannable)
//                    mBinding.topicBottom.topicBottomContainer.visibility = View.VISIBLE
//                    mBinding.topicBottom.tvTopicReply.text = "${card.replycnt}条回复"
//                    mBinding.topicBottom.tvTopicCreated.text = "${card.creator?.username}发起了话题"
//                }
//
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
//        setOnClickListener({
//            LogUtils.e("--------card$card")
//        })
    }

}