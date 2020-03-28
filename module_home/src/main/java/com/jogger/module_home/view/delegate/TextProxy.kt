package com.jogger.module_home.view.delegate

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jogger.constant.CARD_CATEGORY
import com.jogger.module_home.databinding.HomeDetailTextViewBinding

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
class TextProxy(binding: HomeDetailTextViewBinding, context: Context) :
    BaseProxy<HomeDetailTextViewBinding>(binding, context) {

    override fun initView() {
        val card = mBinding.textCard!!
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView
        image =
            if (imgShow == 1) {
                mBinding.nsvCommonView.visibility = View.VISIBLE
                mBinding.ivHeader
            } else if (imgShow == 0) {
                mBinding.nsvCommonView.visibility = View.VISIBLE
                mBinding.ivHeader2
            } else {
                mBinding.clImgTxtView.visibility = View.VISIBLE
                mBinding.ivHeader3
            }
        if (!TextUtils.isEmpty(card.picpath)) {
            image.visibility = View.VISIBLE
            Glide.with(mContext)
                .load(card.picpath)
                .into(image)
        }
        if (!TextUtils.isEmpty(card.showtime)) {
            val date = card.showtime!!.split("-", " ")
            mBinding.tabView.tvDate.text = "${date[0]}\n/\n${date[1]}\n/\n${date[2]}"
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
        var category = ""
        if (card.original == 1)
            category += "#原创 "
        when (card.category) {
            CARD_CATEGORY.TYPE_TEXT._value ->
                mBinding.tvCategory.text = category + "#文字"
            CARD_CATEGORY.TYPE_POETRY._value -> mBinding.tvCategory.text = category + "#诗"
            CARD_CATEGORY.TYPE_FILM._value -> mBinding.tvCategory.text = category + "#电影"
            CARD_CATEGORY.TYPE_RECORD._value -> mBinding.tvCategory.text = category + "#语录"
            CARD_CATEGORY.TYPE_WORD._value -> mBinding.tvCategory.text = category + "#歌词"
        }
//        setOnClickListener({
//            LogUtils.e("--------card$card")
//        })
    }

}