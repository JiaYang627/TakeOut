package com.jiayang.takeout.common;

import android.app.Application;

import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.m.component.AppComponent;
import com.jiayang.takeout.m.component.DaggerApiComponent;
import com.jiayang.takeout.m.component.DaggerAppComponent;
import com.jiayang.takeout.m.model.ApiModule;
import com.jiayang.takeout.m.model.AppModule;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.utils.ToastUtils;

/**
 * Created by 张 奎 on 2017-09-11 15:07.
 */

public class TUAppDeletage {

    private Application appContext;
    private ApiComponent apiComponent;
    private AppComponent appComponent;


    public TUAppDeletage(Application appContext) {
        this.appContext = appContext;
    }

    public void onCreate() {
        inject();
        ToastUtils.init(appContext);        //吐司初始化
        PreferenceTool.init(appContext);    //Preference参数
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(getAppModule())
                .build();


        apiComponent = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .appModule(getAppModule())
                .build();
    }



    public void onTerminate() {
        this.appComponent = null;
        this.apiComponent = null;
        this.appComponent = null;
    }

    private AppModule getAppModule() {
        return new AppModule(appContext);
    }

    public ApiComponent getApiComponent(){
        return apiComponent;
    }

}
