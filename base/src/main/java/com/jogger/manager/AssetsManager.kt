package com.jogger.manager

import android.graphics.Typeface
import ex.sApplication
import java.lang.reflect.Type


class AssetsManager {
    companion object {
        const val ASSETS_FONT1 = "fonts/fzqkyuesong.TTF"
        const val ASSETS_FONT2 = "fonts/fzss.ttf"
        const val ASSETS_FONT3 = "fonts/fzsx.ttf"
        const val ASSETS_FONT4 = "fonts/fzzxhjt.ttf"
        const val ASSETS_FONT5 = "fonts/fzsy.ttf"
        val mgr = sApplication.assets
        fun getFontTypeFace(asset: String): Typeface =Typeface.createFromAsset(mgr, asset)

        fun getTitleTypeFace():Typeface= getFontTypeFace(ASSETS_FONT2)
    }
}