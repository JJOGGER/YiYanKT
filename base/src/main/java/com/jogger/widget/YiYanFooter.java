package com.jogger.widget;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import com.jogger.base.R;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.SmartUtil;

import java.util.Objects;

/**
 * Created by jogger on 2020/4/1
 * 描述：
 */
public class YiYanFooter extends InternalAbstract implements RefreshFooter {

    //<editor-fold desc="属性变量">

    protected boolean mManualNormalColor;
    protected boolean mManualAnimationColor;

    protected Paint mPaint;

    protected Paint mTextPaint;
    protected int mNormalColor = 0xffeeeeee;
    protected int mAnimatingColor = 0xffe75946;

    protected float mCircleSpacing;

    protected long mStartTime = 0;
    protected boolean mIsStarted = false;
    protected TimeInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
    protected boolean mNoMoreData = false;
    //</editor-fold>

    //<editor-fold desc="构造方法">
    public YiYanFooter(Context context) {
        this(context, null);
    }

    public YiYanFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

        final View thisView = this;
        thisView.setMinimumHeight(SmartUtil.dp2px(60));
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BallPulseFooter);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mTextPaint = new Paint();
        QMUISkinValueBuilder acquire = QMUISkinValueBuilder.acquire();
        acquire.textColor(R.attr.app_skin_common_title_text_color);
        QMUISkinHelper.setSkinValue(this, acquire);
        acquire.release();
        mTextPaint.setColor(Objects.requireNonNull(QMUIResHelper.getAttrColorStateList(
                context,
                QMUISkinManager.defaultInstance(context).getCurrentTheme(),
                R.attr.app_skin_common_title_text_color
        )).getDefaultColor());
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.text_size_16));
        mTextPaint.setAntiAlias(true);
        mSpinnerStyle = SpinnerStyle.Translate;
        mSpinnerStyle = SpinnerStyle.values[ta.getInt(R.styleable.BallPulseFooter_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal)];

        if (ta.hasValue(R.styleable.BallPulseFooter_srlNormalColor)) {
            setNormalColor(ta.getColor(R.styleable.BallPulseFooter_srlNormalColor, 0));
        }
        if (ta.hasValue(R.styleable.BallPulseFooter_srlAnimatingColor)) {
            setAnimatingColor(ta.getColor(R.styleable.BallPulseFooter_srlAnimatingColor, 0));
        }

        ta.recycle();

        mCircleSpacing = SmartUtil.dp2px(4);

    }

    //</editor-fold>

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final View thisView = this;
        final int width = thisView.getWidth();
        final int height = thisView.getHeight();
        if (mNoMoreData) {
            String content = "- End -";
            float textWith = mTextPaint.measureText(content);
            canvas.drawText(content, width / 2f - textWith / 2, height / 2f, mTextPaint);
            return;
        }
        float radius = (Math.min(width, height) - mCircleSpacing * 2) / 6;
        float x = width / 2f - (radius * 2 + mCircleSpacing);
        float y = height / 2f;

        final long now = System.currentTimeMillis();

        for (int i = 0; i < 3; i++) {

            long time = now - mStartTime - 120 * (i + 1);
            float percent = time > 0 ? ((time % 750) / 750f) : 0;
            percent = mInterpolator.getInterpolation(percent);

            canvas.save();

            float translateX = x + (radius * 2) * i + mCircleSpacing * i;
            canvas.translate(translateX, y);

            if (percent < 0.5) {
                float scale = 1 - percent * 2 * 0.7f;
                canvas.scale(scale, scale);
            } else {
                float scale = percent * 2 * 0.7f - 0.4f;
                canvas.scale(scale, scale);
            }

            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }

        super.dispatchDraw(canvas);

        if (mIsStarted) {
            thisView.invalidate();
        }
    }


    //<editor-fold desc="刷新方法 - RefreshFooter">
    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        if (mIsStarted) return;
        if (mNoMoreData) return;
        final View thisView = this;
        thisView.invalidate();
        mIsStarted = true;
        mStartTime = System.currentTimeMillis();
        mPaint.setColor(mAnimatingColor);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (mNoMoreData) return 0;
        mIsStarted = false;
        mStartTime = 0;
        mPaint.setColor(mNormalColor);
        return 0;
    }

    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (!mManualAnimationColor && colors.length > 1) {
            setAnimatingColor(colors[0]);
            mManualAnimationColor = false;
        }
        if (!mManualNormalColor) {
            if (colors.length > 1) {
                setNormalColor(colors[1]);
            } else if (colors.length > 0) {
                setNormalColor(ColorUtils.compositeColors(0x99ffffff, colors[0]));
            }
            mManualNormalColor = false;
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            invalidate();
//            final View arrowView = mArrowView;
//            if (noMoreData) {
//                mTitleText.setText(mTextNothing);
//                arrowView.setVisibility(GONE);
//            } else {
//                mTitleText.setText(mTextPulling);
//                arrowView.setVisibility(VISIBLE);
//            }
        }
        return true;
    }

    //</editor-fold>

    //<editor-fold desc="开放接口 - API">
    public YiYanFooter setSpinnerStyle(SpinnerStyle mSpinnerStyle) {
        this.mSpinnerStyle = mSpinnerStyle;
        return this;
    }

    public YiYanFooter setNormalColor(@ColorInt int color) {
        mNormalColor = color;
        mManualNormalColor = true;
        if (!mIsStarted) {
            mPaint.setColor(color);
        }
        return this;
    }

    public YiYanFooter setAnimatingColor(@ColorInt int color) {
        mAnimatingColor = color;
        mManualAnimationColor = true;
        if (mIsStarted) {
            mPaint.setColor(color);
        }
        return this;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        if (mNoMoreData) return;
        super.onStateChanged(refreshLayout, oldState, newState);
    }
}

