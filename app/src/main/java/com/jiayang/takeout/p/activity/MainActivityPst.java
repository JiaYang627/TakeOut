package com.jiayang.takeout.p.activity;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.ImainAcitivityView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class MainActivityPst extends BasePresenter<ImainAcitivityView> {


    private TakeOutService takeOutService;



    @Inject
    public MainActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        this.takeOutService = takeOutService;
    }



}
