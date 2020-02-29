package com.jogger.yiyan.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.IdRes

/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class BottomGroup(context: Context?, attrs: AttributeSet?) : RadioGroup(context, attrs) {
    private var mBottomBarCheckedChangeListener: OnBottomBarCheckedChangeListener? = null
    private val mTitles = arrayListOf<RadioButton>()
    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)
        if (child is RadioButton && child.getVisibility() == View.VISIBLE) run {
            mTitles.add(child)
            child.setOnClickListener { v ->
                child.isChecked = true
                checkRadioButton(child)
                if (mBottomBarCheckedChangeListener != null) {
                    val dex = mTitles.indexOf(child)
                    mBottomBarCheckedChangeListener!!.onBottomBarCheckedChanged(dex, child.getId())
                }
            }
        }
    }

    private fun checkRadioButton(radioButton: RadioButton) {
        for (i in mTitles.indices) {
            val rbutton = mTitles.get(i)
            if (rbutton !== radioButton) {
                rbutton.setChecked(false)
            }
        }
    }

    interface OnBottomBarCheckedChangeListener {
        fun onBottomBarCheckedChanged(index: Int, @IdRes checkedId: Int)
    }

    fun setOnBottomBarCheckedChangedListener(listener: OnBottomBarCheckedChangeListener) {
        this.mBottomBarCheckedChangeListener = listener
    }

    fun setRadioButtonChecked(index: Int) {
        mTitles.get(index).setChecked(true)
    }
}