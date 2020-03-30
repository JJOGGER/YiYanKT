package com.jogger.utils

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jogger.entity.TextCard
import com.jogger.manager.AssetsManager
import com.qmuiteam.qmui.qqface.QMUIQQFaceView

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

    @BindingAdapter("app:textCard")
    @JvmStatic
    fun setText(view: QMUIQQFaceView, textCard: TextCard) {
        view.setTypeface(
            AssetsManager.getTypeFaceByType(
                textCard.type?.split("_")?.get(3)?.toInt() ?: 0
            )
        )
    }

    @BindingAdapter("app:typeface")
    @JvmStatic
    fun setTypeface(view: TextView, type: Int) {
        if (view.typeface == Typeface.DEFAULT)
            view.typeface = AssetsManager.getTypeFaceByType(type)
    }

    @BindingAdapter("app:typeface")
    @JvmStatic
    fun setTypeface(view: QMUIQQFaceView, type: Int) {
        view.setTypeface(AssetsManager.getTypeFaceByType(type))
    }
}