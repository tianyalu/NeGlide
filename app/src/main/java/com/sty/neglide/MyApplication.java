package com.sty.neglide;

import android.app.Application;

/**
 * Created by tian on 2019/10/12.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }
}
