package com.jogger.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class LineView : View {
    private lateinit var mPaint:Paint
    private lateinit var mPath: Path
    constructor(context: Context):this(context,null)

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        init(context)
    }

    fun init(context: Context){
        mPaint= Paint()
        mPaint.color=Color.BLACK
        mPaint.isAntiAlias=true
        mPaint.strokeWidth=4f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.lineTo(0f,100f)
        canvas.drawPath(mPath,mPaint)
    }
}