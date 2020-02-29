package com.qmuiteam.qmui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jogger on 2019/3/11
 * 描述：
 */
public class QMUIContext {
    private static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static Context getApplication() {
        if (sContext != null) return sContext;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            sContext = (Application) app;
            return sContext;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Context getContext() {
        return getApplication();
    }
}
