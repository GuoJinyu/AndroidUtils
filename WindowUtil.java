package com.example.jiangrui.myapplication;

import android.app.Activity;
import android.graphics.Rect;

import java.lang.reflect.Field;

/**
 * Android Window相关工具类
 */
public class WindowUtil {

    // 获取设备状态栏高度，px值
    // 使用了两种方式。如果方式一不起作用，则采用方式二（反射）
    public int getStatusBarHeight(Activity myActivityReference) {
        Rect frame = new Rect();
        myActivityReference.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        if (statusBarHeight == 0) {
            Class<?> c;
            Object obj;
            Field field;
            int x;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = myActivityReference.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
