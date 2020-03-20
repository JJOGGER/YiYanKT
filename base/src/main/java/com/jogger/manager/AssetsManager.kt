package com.jogger.manager

import android.graphics.Typeface
import ex.sApplication


class AssetsManager {
    companion object {
        const val ASSETS_FONT1 = "fonts/fzqkyuesong.TTF"
        const val ASSETS_FONT2 = "fonts/fzss.ttf"
        const val ASSETS_FONT3 = "fonts/fzsx.ttf"
        const val ASSETS_FONT4 = "fonts/fzzxhjt.ttf"
        const val ASSETS_FONT5 = "fonts/fzsy.ttf"
        //yyh_0_0_0_0 第1位1代表横向大图,0代表圆图，11代表方正图 88代表占满宽度,且文字排图片里  第2位1代表横向，0代表竖向 第三位代表字体 第4为1代表局左，0代表居中
        val mgr = sApplication.assets

        fun getFontTypeFace(asset: String): Typeface = Typeface.createFromAsset(mgr, asset)

        fun getTitleTypeFace(): Typeface = getFontTypeFace(ASSETS_FONT2)
        fun getTypeFaceByType(type: Int): Typeface {
            return when (type) {
                1 -> {
                    getFontTypeFace(ASSETS_FONT2)
                }
                2 -> {
                    getFontTypeFace(ASSETS_FONT3)
                }
                3 -> {
                    getFontTypeFace(ASSETS_FONT4)
                }
                4 -> {
                    getFontTypeFace(ASSETS_FONT5)
                }
                else -> getFontTypeFace(ASSETS_FONT1)
            }
        }
    }
}