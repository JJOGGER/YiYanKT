package com.jogger.module_star.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.jogger.manager.AssetsManager
import com.qmuiteam.qmui.util.QMUIDisplayHelper


/**
 * Created by jogger on 2020/3/4
 * 描述：
 */
class CustomTabLayout : TabLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dp10 = QMUIDisplayHelper.dp2px(context, 10)
        val mTabStrip = this.getChildAt(0) as LinearLayout
        try {
            val mTabs = TabLayout::class.java.getDeclaredField("mTabs")
            mTabs.isAccessible = true
            val tabs = mTabs.get(this) as ArrayList<*>
            for (i in 0 until mTabStrip.childCount) {
                val tab = tabs[i]
                val mView = tab.javaClass.getDeclaredField("mView")
                mView.isAccessible = true
                val tabView = mView.get(tab)
                val mTextView = context.classLoader.loadClass("android.support.design.widget.TabLayout\$TabView")
                    .getDeclaredField("mTextView")
                mTextView.isAccessible = true
                val textView = mTextView.get(tabView) as TextView
                textView.setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT2))
                val textWidth = textView.paint.measureText(textView.text.toString())
                val child = mTabStrip.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(textWidth.toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
                params.leftMargin = dp10
                params.rightMargin = dp10
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}