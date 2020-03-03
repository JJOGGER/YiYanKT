package com.jogger.module_home.view.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.jogger.constant.CardCategory
import com.jogger.entity.TextCard
import com.jogger.module_home.R
import com.jogger.module_home.databinding.HomeTextCardContainerLayoutBinding

/**
 * Created by jogger on 2020/3/3
 * 描述：
 */
class TextCardContainer : FrameLayout {
    private lateinit var mBinding: HomeTextCardContainerLayoutBinding

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context?) {
//        LayoutInflater.from(context).inflate( R.layout.home_text_card_container_layout,this)
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.home_text_card_container_layout,
            this,
            true
        )
    }

    fun setData(card: TextCard) {
        Glide.with(this)
            .load(card.picpath)
            .into(mBinding.ivHeader)
        if (!TextUtils.isEmpty(card.title))
            mBinding.tvTitle.setText(card.title)
        when (card.category) {
            CardCategory.TYPE_TEXT.ordinal -> {
                mBinding.textContainer.visibility = View.VISIBLE
                mBinding.tvContent.text = card.content
            }
            CardCategory.TYPE_MUSIC.ordinal -> {
                mBinding.musicContainer.visibility = View.VISIBLE
            }
        }
    }

}