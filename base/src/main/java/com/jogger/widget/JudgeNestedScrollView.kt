package com.jogger.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 * Created by jogger on 2020/3/27
 * 描述：
 */
class JudgeNestedScrollView(context: Context, attrs: AttributeSet?) : NestedScrollView(context, attrs) {
    private var mIsNeedScroll = true
    private var xDistance: Float = 0f
    var yDistance: Float = 0f
    var xLast: Float = 0f
    var yLast: Float = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            xDistance = 0f
            yDistance = 0f
            xLast = ev.getX()
            yLast = ev.getY()
        } else if (ev.action == MotionEvent.ACTION_MOVE) {
            val curX = ev.getX()
            val curY = ev.getY()
            xDistance += Math.abs(curX - xLast);
            yDistance += Math.abs(curY - yLast);
            xLast = curX
            yLast = curY
            if (xDistance > yDistance) {
                return false
            }
            return mIsNeedScroll
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun setNeedScroll(isNeedScroll: Boolean) {
        mIsNeedScroll = isNeedScroll
    }
}