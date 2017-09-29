package com.jiayang.takeout.p.activity;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IorderDetailActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-29 11:45.
 */

public class OrderDetailActivityPst extends BasePresenter<IorderDetailActivityView> {


    private final TakeOutService mTakeOutService;

    @Inject
    public OrderDetailActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }
}
