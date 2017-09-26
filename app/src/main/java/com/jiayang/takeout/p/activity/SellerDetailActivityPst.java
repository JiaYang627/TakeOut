package com.jiayang.takeout.p.activity;

import android.content.Intent;

import com.jiayang.takeout.common.TakeOutNavigete;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.iview.IsellerDetailActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-16 09:36.
 */

public class SellerDetailActivityPst extends BasePresenter<IsellerDetailActivityView> {

    public final static String EXTRA_SELLERID_ID = "sellerId";
    public final static String EXTRA_SELLERNAME_ID = "sellerName";
    private TakeOutService mTakeOutService;

    @Inject
    public SellerDetailActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        this.mTakeOutService = takeOutService;
    }



    @Override
    public void getData(Intent intent) {
        super.getData(intent);
        long sellerId = intent.getLongExtra(SellerDetailActivityPst.EXTRA_SELLERID_ID, -1);
        String sellerName = intent.getStringExtra(SellerDetailActivityPst.EXTRA_SELLERNAME_ID);
        mView.initToolBar(sellerName, sellerId);
    }

}
