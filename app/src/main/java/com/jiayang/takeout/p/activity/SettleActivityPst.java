package com.jiayang.takeout.p.activity;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IsettleActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-24 09:24.
 */

public class SettleActivityPst extends BasePresenter<IsettleActivityView> {

    private TakeOutService mTakeOutService;

    @Inject
    public SettleActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }
}
