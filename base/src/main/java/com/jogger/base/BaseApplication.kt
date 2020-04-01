package com.jogger.base

import android.app.Application
import android.content.res.Configuration
import com.jogger.manager.QDSkinManager
import ex.initApp

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initApp(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_DARK)
        } else if (QDSkinManager.getCurrentSkin() == QDSkinManager.SKIN_DARK) {
            QDSkinManager.changeSkin(QDSkinManager.SKIN_WHITE)
        }
    }
}