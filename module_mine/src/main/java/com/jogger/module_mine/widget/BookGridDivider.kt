package com.jogger.module_mine.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by jogger on 2020/3/19
 * 描述：
 */
class BookGridDivider(space: Int) : RecyclerView.ItemDecoration() {
    private val mSpace: Int = space

    companion object {
        fun newInstanceWithSpacePx(space: Int): BookGridDivider {
            return BookGridDivider(space)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = mSpace
        outRect.right = mSpace
        outRect.top = mSpace
        outRect.bottom = mSpace
    }
}