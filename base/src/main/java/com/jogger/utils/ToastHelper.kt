package com.jogger.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import ex.sApplication

/**
 * Created by jogger on 2020/2/26
 * 描述：
 */
object ToastHelper {
    private var sToast: Toast? = null
    fun showToast(text: String) {
        showToastInner(sApplication, text, Toast.LENGTH_SHORT)
    }

    fun showToast(resId: Int) {
        showToastInner(sApplication, sApplication.getString(resId), Toast.LENGTH_SHORT)
    }

    private fun showToastInner(context: Context, text: String, duration: Int) {
        ensureToast(context)
        sToast!!.setText(text)
        sToast!!.setDuration(duration)
        sToast!!.show()
    }

    @SuppressLint("ShowToast")
    private fun ensureToast(context: Context) {
        if (sToast != null) {
            return
        }
        synchronized(ToastHelper::class.java) {
            if (sToast != null) {
                return
            }
            sToast = Toast.makeText(context.applicationContext, " ", Toast.LENGTH_SHORT)
        }
    }
}