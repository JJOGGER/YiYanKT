package com.jogger.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager


/**
 * Created by jogger on 2020/2/29
 * 描述：
 */
class HomeViewPager : ViewPager {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setPageTransformer(true, MyZoomOutPageTransformer())
        setCurrentItem(0, false)//触发onPageScrolled，防止初始化时transformPage 所有position都为0
    }

    inner class MyZoomOutPageTransformer : PageTransformer {

        override fun transformPage(page: View, position: Float) {
            //setScaleY只支持api11以上
            if (position < -1) {
                page.setScaleX(MIN_SCALE)
                page.setScaleY(MIN_SCALE)
            } else if (position <= 1)
            //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                //              Log.e("TAG", view + " , " + position + "");
                val scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE)
                page.setScaleX(scaleFactor)
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    page.setTranslationX(-scaleFactor * 2)
                } else if (position < 0) {
                    page.setTranslationX(scaleFactor * 2)
                }
                page.setScaleY(scaleFactor)

            } else { // (1,+Infinity]

                page.setScaleX(MIN_SCALE)
                page.setScaleY(MIN_SCALE)

            }
        }

        //自由控制缩放比例
        private val MAX_SCALE = 1f
        private val MIN_SCALE = 0.95f//0.85f
    }
}