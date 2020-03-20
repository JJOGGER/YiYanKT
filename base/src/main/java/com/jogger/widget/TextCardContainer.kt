package com.jogger.widget

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.jogger.base.R
import com.jogger.base.databinding.HomeTextCardContainerLayoutBinding
import com.jogger.constant.CARD_CATEGORY
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.jogger.utils.LogUtils


/**
 * Created by jogger on 2020/3/3
 * 描述：
 */
class TextCardContainer : FrameLayout {
    private lateinit var mBinding: HomeTextCardContainerLayoutBinding
    var spannable = SpannableStringBuilder("[icon] ")

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context?) {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.home_text_card_container_layout,
            this,
            true
        )
        val drawable = this.resources.getDrawable(R.drawable.icon_topicmark_3x)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan1 = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    fun setData(card: TextCard) {
        val imgShow = card.type?.split("_")?.get(1)?.toInt()
        val image: ImageView
        if (imgShow == 1) {
            image = mBinding.headerLayout.ivHeader
        } else {
            image = mBinding.headerLayout.ivHeader2
            if (imgShow == 0) {
                image.isCircle = false
            }
        }
        if (!TextUtils.isEmpty(card.picpath)) {
            mBinding.headerLayout.flHeader.visibility = View.VISIBLE
            image.visibility = View.VISIBLE
            Glide.with(this)
                .load(card.picpath)
                .into(image)
        } else {
            mBinding.headerLayout.flHeader.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(card.title)) {
            mBinding.textLayout.tvTextTitle.text = card.title
        }
        if (!TextUtils.isEmpty(card.showtime)) {
            mBinding.tvDate.visibility = View.VISIBLE
            val date = card.showtime!!.split("-", " ")
            mBinding.tvDate.text = "${date[0]}\n/\n${date[1]}\n/\n${date[2]}"
        }
        if (card.creator != null && card.originbook != null)
            mBinding.tvCreated.text = "${card.creator!!.username} 创建于 [${card.originbook!!.bookname}]"
        mBinding.commonBottom.tvCollection.text = "${card.collectcnt}"
        mBinding.commonBottom.tvComment.text = "${card.replycnt}"
        mBinding.commonBottom.tvLike.text = "${(card.commentcnt - card.replycnt)}"
        mBinding.tvCreated.typeface = AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT4)
        mBinding.tvCategory.typeface = mBinding.tvCreated.typeface
        mBinding.topicBottom.tvTopicReply.typeface = mBinding.tvCreated.typeface
        mBinding.topicBottom.tvTopicCreated.typeface = mBinding.tvCreated.typeface
        mBinding.textLayout.tvTextTitle.typeface =
            AssetsManager.getTypeFaceByType(
                card.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        mBinding.textLayout.tvContent.typeface = mBinding.textLayout.tvTextTitle.typeface
        mBinding.textLayout.tvTextFrom.typeface = mBinding.textLayout.tvTextTitle.typeface
        mBinding.musicLayout.tvMusicTitle.typeface = mBinding.textLayout.tvTextTitle.typeface
        mBinding.musicLayout.tvMusicFrom.typeface = mBinding.textLayout.tvTextTitle.typeface
//        mBinding.textLayout.tvContent.isVerticalMode = card.type?.split("_")?.get(2)?.toInt() == 0
        mBinding.textLayout.tvContent.gravity =
            if (card.type?.split("_")?.get(4)?.toInt() == 1) Gravity.START else Gravity.CENTER_HORIZONTAL
        when (card.category) {
            CARD_CATEGORY.TYPE_TEXT._value,
            CARD_CATEGORY.TYPE_TOPIC._value,
            CARD_CATEGORY.TYPE_POETRY._value -> {
                mBinding.textLayout.textContainer.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(card.from))
                    mBinding.textLayout.tvTextFrom.text = "- ${card.from} -"
                if (card.category == CARD_CATEGORY.TYPE_TEXT._value || card.category == CARD_CATEGORY.TYPE_POETRY._value) {
                    mBinding.commonBottomContainer.visibility = View.VISIBLE
                    mBinding.textLayout.tvContent.text = card.content
                    mBinding.tvCategory.text = "#文字"
                } else {
                    spannable.append(card.title)
                    mBinding.textLayout.tvTextTitle.setText(spannable)
                    mBinding.topicBottom.topicBottomContainer.visibility = View.VISIBLE
                    mBinding.topicBottom.tvTopicReply.text = "${card.replycnt}条回复"
                    mBinding.topicBottom.tvTopicCreated.text = "${card.creator?.username}发起了话题"
                }

            }
            CARD_CATEGORY.TYPE_MUSIC._value -> {
                mBinding.musicLayout.musicContainer.visibility = View.VISIBLE
                mBinding.commonBottomContainer.visibility = View.VISIBLE
                if (!TextUtils.isEmpty(card.title))
                    mBinding.musicLayout.tvMusicTitle.text = card.title
                if (!TextUtils.isEmpty(card.from))
                    mBinding.musicLayout.tvMusicFrom.text = "- ${card.from} -"
            }
        }
        setOnClickListener({
            LogUtils.e("--------card$card")
        })

    }

}