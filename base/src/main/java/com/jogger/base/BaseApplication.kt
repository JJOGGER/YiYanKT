package com.jogger.base

import android.app.Application
import ex.initApp

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initApp(this)
    }
}