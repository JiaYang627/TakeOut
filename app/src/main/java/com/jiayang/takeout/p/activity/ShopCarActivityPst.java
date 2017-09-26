package com.jiayang.takeout.p.activity;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IshopCarActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-20 17:17.
 */

public class ShopCarActivityPst extends BasePresenter<IshopCarActivityView> {


    private TakeOutService mTakeOutService;

    @Inject
    public ShopCarActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    public void goToSettle() {
        mTakeOutNavigete.goToSettle(context);
    }
}
