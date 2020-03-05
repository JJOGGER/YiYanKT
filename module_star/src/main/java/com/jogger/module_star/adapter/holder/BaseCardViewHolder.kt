package com.jogger.module_star.adapter.holder

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jogger.entity.TextCard
import com.qmuiteam.qmui.widget.QMUIVerticalTextView

abstract class BaseCardViewHolder(view: View, context: Context) : BaseViewHolder(view) {
    val mContext = context
    open abstract fun convert(item: TextCard)

    open fun setTypeFace(vararg @IdRes viewIds: Int, typeface: Typeface): BaseCardViewHolder {
        for (it in viewIds) {
            getView<TextView>(it).typeface = typeface
        }
        return this
    }

    open fun isVerticalMode(@IdRes viewId: Int, isVerticalMode: Boolean) {
        getView<QMUIVerticalTextView>(viewId).isVerticalMode = isVerticalMode
    }
    open fun setGravity(@IdRes viewId: Int, gravity: Int){
        getView<TextView>(viewId).gravity=gravity
    }

}