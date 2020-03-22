package com.jogger.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager

/**
 * Created by jogger on 2020/3/20
 * 描述：
 */
object TextViewAttrAdapter {
    @BindingAdapter("app:textCard")
    @JvmStatic
    fun setText(view: TextView, textCard: TextCard) {
        view.typeface =
            AssetsManager.getTypeFaceByType(
                textCard.type?.split("_")?.get(3)?.toInt() ?: 0
            )
    }

}