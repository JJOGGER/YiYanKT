package com.jogger.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.jogger.manager.AssetsManager

class CommonTextView:TextView {
    constructor(context: Context):this(context,null)

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        setTypeface(AssetsManager.getFontTypeFace(AssetsManager.ASSETS_FONT4))
    }
}