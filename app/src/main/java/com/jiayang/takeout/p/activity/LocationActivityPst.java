package com.jiayang.takeout.p.activity;

import android.app.Activity;
import android.content.Intent;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IlocationActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-28 15:06.
 */

public class LocationActivityPst extends BasePresenter<IlocationActivityView> {


    private TakeOutService mTakeOutService;

    @Inject
    public LocationActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }


}
