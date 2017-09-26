package com.jiayang.takeout.m.component;


import com.jiayang.takeout.common.TUAppDeletage;
import com.jiayang.takeout.m.model.AppModule;

import dagger.Component;

/**
 * Created by 张 奎 on 2017-09-01 09:02.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(TUAppDeletage mvpAppDeletage);
}
