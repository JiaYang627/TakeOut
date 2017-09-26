package com.jiayang.takeout.m.model;

import android.app.Application;

import com.jiayang.takeout.common.TakeOutNavigete;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 张 奎 on 2017-09-01 08:58.
 */
@Module
public class AppModule {


    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    TakeOutNavigete provideWyNavigate() {
        return new TakeOutNavigete();
    }
}
