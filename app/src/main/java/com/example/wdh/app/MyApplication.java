package com.example.wdh.app;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        x.Ext.init(this); // XUtils初始化
    }

    public static Context getContext() {
        return context;
    }

    private static String LoginId;

    public static String getLoginId() {
        return LoginId;
    }

    public static void setLoginId(String loginId) {
        LoginId = loginId;
    }
}
