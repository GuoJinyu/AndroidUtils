package com.acker.android;

import android.util.Log;

import com.acker.android.BuildConfig;

/**
 * @date 2016-11-9 14:05
 * @auther GuoJinyu
 * @description Log工具类，对系统的Log进行包装 1、可以对log进行关闭 2、也可以强制打印log
 */

public class LogUtil {

    private static final String TAG = "LogUtil";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, msg);
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG)
            Log.w(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg);
    }

    // 下面是传入自定义tag,并且指定是否强制打印log
    public static void i(String tag, String msg, boolean isForceLog) {
        if (isForceLog) {
            Log.i(tag, msg);
        } else {
            if (BuildConfig.DEBUG)
                Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg, boolean isForceLog) {
        if (isForceLog) {
            Log.d(tag, msg);
        } else {
            if (BuildConfig.DEBUG)
                Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg, boolean isForceLog) {
        if (isForceLog) {
            Log.e(tag, msg);
        } else {
            if (BuildConfig.DEBUG)
                Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg, boolean isForceLog) {
        if (isForceLog) {
            Log.v(tag, msg);
        } else {
            if (BuildConfig.DEBUG)
                Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg, boolean isForceLog) {
        if (isForceLog) {
            Log.v(tag, msg);
        } else {
            if (BuildConfig.DEBUG)
                Log.w(tag, msg);
        }
    }
}
