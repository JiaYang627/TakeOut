package com.jiayang.takeout.common;

import android.app.Application;
import android.content.Context;

import com.jiayang.takeout.m.component.ApiComponent;

/**
 * Created by 张 奎 on 2017-09-11 15:07.
 */

public class TUApp extends Application {
    private static Context sContext;
//    public static String phone = "";
//    public static int USERID = 0;

    private TUAppDeletage mTuAppDeletage;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTuAppDeletage = new TUAppDeletage(this);
        mTuAppDeletage.onCreate();
        sContext = this;


    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        mTuAppDeletage.onTerminate();
    }

    public ApiComponent getApiComponent(){
        return mTuAppDeletage.getApiComponent();
    }
}
