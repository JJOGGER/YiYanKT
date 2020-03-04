package com.jogger.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import com.jogger.base.R
import com.scwang.smartrefresh.header.storehouse.StoreHouseBarItem
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import com.scwang.smartrefresh.layout.util.SmartUtil
import java.util.*


/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class YiYanHeader : InternalAbstract, RefreshHeader {
    //<editor-fold desc="Field">
    var mItemList: MutableList<StoreHouseBarItem> = ArrayList()

    protected var mScale = 1f
    protected var mLineWidth = -1
    protected var mDropHeight = -1
    protected var mHorizontalRandomness = -1
    protected val mInternalAnimationFactor = 0.7f

    protected var mProgress = 0f

    protected var mDrawZoneWidth = 0
    protected var mDrawZoneHeight = 0
    protected var mOffsetX = 0
    protected var mOffsetY = 0
    protected val mBarDarkAlpha = 0.4f
    protected val mFromAlpha = 1.0f
    protected val mToAlpha = 0.4f

    protected var mLoadingAniDuration = 1000
    protected var mLoadingAniSegDuration = 1000
    protected val mLoadingAniItemDuration = 400

    protected var mTextColor = Color.WHITE
    protected var mBackgroundColor = 0
    protected var mIsInLoading = false
    protected var mEnableFadeAnimation = false
    protected var mMatrix = Matrix()
    protected var mRefreshKernel: RefreshKernel? = null
    protected var mAniController = AniController()
    protected var mTransformation = Transformation()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        val view = LayoutInflater.from(context).inflate(com.jogger.yiyan.R.layout.yiyan_refresh_header, this)
//        addView(LineView(context))
        init(context, attrs)

    }
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        init(context)
//    }

    fun init(context: Context, attrs: AttributeSet?) {
        mLineWidth = SmartUtil.dp2px(1f)
        mDropHeight = SmartUtil.dp2px(40f)
        mHorizontalRandomness = Resources.getSystem().displayMetrics.widthPixels / 2
        mBackgroundColor = Color.TRANSPARENT
        setTextColor(resources.getColor(R.color.refresh_bg))
        val ta = context.obtainStyledAttributes(attrs, R.styleable.StoreHouseHeader)
        mLineWidth = ta.getDimensionPixelOffset(R.styleable.StoreHouseHeader_shhLineWidth, mLineWidth)
        mDropHeight = ta.getDimensionPixelOffset(R.styleable.StoreHouseHeader_shhDropHeight, mDropHeight)
        mEnableFadeAnimation = ta.getBoolean(R.styleable.StoreHouseHeader_shhEnableFadeAnimation, mEnableFadeAnimation)
        if (ta.hasValue(R.styleable.StoreHouseHeader_shhText)) {
            initWithString(ta.getString(R.styleable.StoreHouseHeader_shhText))
        } else {
            initWithString("口")
        }
        ta.recycle()

        val thisView = this
        thisView.minimumHeight = mDrawZoneHeight + SmartUtil.dp2px(40f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val thisView = this
        super.setMeasuredDimension(
            View.resolveSize(super.getSuggestedMinimumWidth(), widthMeasureSpec),
            View.resolveSize(super.getSuggestedMinimumHeight(), heightMeasureSpec)
        )

        mOffsetX = (thisView.measuredWidth - mDrawZoneWidth) / 2
        mOffsetY = (thisView.measuredHeight - mDrawZoneHeight) / 2//getTopOffset();
        mDropHeight = thisView.measuredHeight / 2//getTopOffset();
    }

    override fun dispatchDraw(canvas: Canvas) {

        val thisView = this
        val c1 = canvas.save()
        val len = mItemList.size
        val progress: Float = if (thisView.isInEditMode) 1f else mProgress

        for (i in 0 until len) {

            canvas.save()
            val storeHouseBarItem = mItemList[i]
            var offsetX = mOffsetX + storeHouseBarItem.midPoint.x
            var offsetY = mOffsetY + storeHouseBarItem.midPoint.y

            if (mIsInLoading) {
                storeHouseBarItem.getTransformation(thisView.drawingTime, mTransformation)
                canvas.translate(offsetX, offsetY)
            } else {

                if (progress == 0f) {
                    storeHouseBarItem.resetPosition(mHorizontalRandomness)
                    continue
                }

                val startPadding = (1 - mInternalAnimationFactor) * i / len
                val endPadding = 1f - mInternalAnimationFactor - startPadding

                // done
                if (progress == 1f || progress >= 1 - endPadding) {
                    canvas.translate(offsetX, offsetY)
                    storeHouseBarItem.setAlpha(mBarDarkAlpha)
                } else {
                    val realProgress: Float
                    if (progress <= startPadding) {
                        realProgress = 0f
                    } else {
                        realProgress = Math.min(1f, (progress - startPadding) / mInternalAnimationFactor)
                    }
                    offsetX += storeHouseBarItem.translationX * (1 - realProgress)
                    offsetY += -mDropHeight * (1 - realProgress)
                    mMatrix.reset()
                    mMatrix.postRotate(360 * realProgress)
                    mMatrix.postScale(realProgress, realProgress)
                    mMatrix.postTranslate(offsetX, offsetY)
                    storeHouseBarItem.setAlpha(mBarDarkAlpha * realProgress)
                    canvas.concat(mMatrix)
                }
            }
            storeHouseBarItem.draw(canvas)
            canvas.restore()
        }
        if (mIsInLoading) {
            thisView.invalidate()
        }
        canvas.restoreToCount(c1)

        super.dispatchDraw(canvas)
    }
    //</editor-fold>

    //<editor-fold desc="API">
    fun setLoadingAniDuration(duration: Int): YiYanHeader {
        mLoadingAniDuration = duration
        mLoadingAniSegDuration = duration
        return this
    }

    fun setLineWidth(width: Int): YiYanHeader {
        mLineWidth = width
        for (i in mItemList.indices) {
            mItemList[i].setLineWidth(width)
        }
        return this
    }

    fun setTextColor(@ColorInt color: Int): YiYanHeader {
        mTextColor = color
        for (i in mItemList.indices) {
            mItemList[i].setColor(color)
        }
        return this
    }

    fun setDropHeight(height: Int): YiYanHeader {
        mDropHeight = height
        return this
    }

    fun initWithString(str: String?): YiYanHeader {
        initWithString(str, 25)
        return this
    }

    fun initWithString(str: String?, fontSize: Int): YiYanHeader {
        val pointList = YiYanHeaderPath.getPath(str, fontSize * 0.01f, 14)
        initWithPointList(pointList)
        return this
    }

    fun initWithStringArray(id: Int): YiYanHeader {
        val thisView = this
        val points = thisView.resources.getStringArray(id)
        val pointList = ArrayList<FloatArray>()
        for (point in points) {
            val x = point.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val f = FloatArray(4)
            for (j in 0..3) {
                f[j] = java.lang.Float.parseFloat(x[j])
            }
            pointList.add(f)
        }
        initWithPointList(pointList)
        return this
    }

    fun setScale(scale: Float): YiYanHeader {
        mScale = scale
        return this
    }

    fun initWithPointList(pointList: List<FloatArray>): YiYanHeader {

        var drawWidth = 0f
        var drawHeight = 0f
        val shouldLayout = mItemList.size > 0
        mItemList.clear()
        for (i in pointList.indices) {
            val line = pointList[i]
            val startPoint = PointF(SmartUtil.dp2px(line[0]) * mScale, SmartUtil.dp2px(line[1]) * mScale)
            val endPoint = PointF(SmartUtil.dp2px(line[2]) * mScale, SmartUtil.dp2px(line[3]) * mScale)

            drawWidth = Math.max(drawWidth, startPoint.x)
            drawWidth = Math.max(drawWidth, endPoint.x)

            drawHeight = Math.max(drawHeight, startPoint.y)
            drawHeight = Math.max(drawHeight, endPoint.y)

            val item = StoreHouseBarItem(i, startPoint, endPoint, mTextColor, mLineWidth)
            item.resetPosition(mHorizontalRandomness)
            mItemList.add(item)
        }
        mDrawZoneWidth = Math.ceil(drawWidth.toDouble()).toInt()
        mDrawZoneHeight = Math.ceil(drawHeight.toDouble()).toInt()
        if (shouldLayout) {
            val thisView = this
            thisView.requestLayout()
        }
        return this
    }

    override fun onInitialized(@NonNull kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        mRefreshKernel = kernel
        mRefreshKernel!!.requestDrawBackgroundFor(this, mBackgroundColor)
    }

    override fun onMoving(isDragging: Boolean, percent: Float, offset: Int, height: Int, maxDragHeight: Int) {
        mProgress = percent * .8f
        invalidate()
    }

    override fun onReleased(@NonNull layout: RefreshLayout, height: Int, maxDragHeight: Int) {
        mIsInLoading = true
        mAniController.start()
        val thisView = this
        thisView.invalidate()
    }

    override fun onFinish(@NonNull layout: RefreshLayout, success: Boolean): Int {
        mIsInLoading = false
        mAniController.stop()
        if (success && mEnableFadeAnimation) {
            val thisView = this
            thisView.startAnimation(object : Animation() {
                init {
                    super.setDuration(250)
                    super.setInterpolator(AccelerateInterpolator())
                }

                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    mProgress = 1 - interpolatedTime
                    invalidate()
                    if (interpolatedTime == 1f) {
                        for (i in mItemList.indices) {
                            mItemList[i].resetPosition(mHorizontalRandomness)
                        }
                    }
                }
            })
            return 250
        } else {
            for (i in mItemList.indices) {
                mItemList[i].resetPosition(mHorizontalRandomness)
            }
        }
        return 0
    }

    /**
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Deprecated("请使用 {@link RefreshLayout#setPrimaryColorsId(int...)}")
    override fun setPrimaryColors(@ColorInt vararg colors: Int) {
        if (colors.size > 0) {
            mBackgroundColor = colors[0]
            if (mRefreshKernel != null) {
                mRefreshKernel!!.requestDrawBackgroundFor(this, mBackgroundColor)
            }
            if (colors.size > 1) {
                setTextColor(colors[1])
            }
        }
    }
    //</editor-fold>

    protected inner class AniController : Runnable {

        internal var mTick = 0
        internal var mCountPerSeg = 0
        internal var mSegCount = 0
        internal var mInterval = 0
        internal var mRunning = true

        fun start() {
            mRunning = true
            mTick = 0

            mInterval = mLoadingAniDuration / mItemList.size
            mCountPerSeg = mLoadingAniSegDuration / mInterval
            mSegCount = mItemList.size / mCountPerSeg + 1
            run()
        }

        override fun run() {

            val pos = mTick % mCountPerSeg
            for (i in 0 until mSegCount) {

                var index = i * mCountPerSeg + pos
                if (index > mTick) {
                    continue
                }

                index = index % mItemList.size
                val item = mItemList[index]

                item.fillAfter = false
                item.isFillEnabled = true
                item.fillBefore = false
                item.duration = mLoadingAniItemDuration.toLong()
                item.start(mFromAlpha, mToAlpha)
            }

            mTick++
            if (mRunning && mRefreshKernel != null) {
                val refreshView = mRefreshKernel!!.getRefreshLayout().layout
                refreshView.postDelayed(this, mInterval.toLong())
            }
        }

        fun stop() {
            mRunning = false
            removeCallbacks(this)
        }
    }
}