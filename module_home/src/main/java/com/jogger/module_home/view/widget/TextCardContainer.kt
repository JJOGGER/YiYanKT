package com.jogger.module_home.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
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
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    fun init(context: Context?, attrs: AttributeSet?) {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.home_text_card_container_layout,
            this,
            true
        )
    }

    fun setHeaderImage(imgUrl: String) {

    }

}