package com.jiayang.takeout.p.activity;

import android.content.Intent;

import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.v.iview.IonLinePayActivityView;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by 张 奎 on 2017-09-27 15:59.
 */

public class OnLinePayActivityPst extends BasePresenter<IonLinePayActivityView> {


    public static String EXTRA_ORDER_ID = "orderId";
    private TakeOutService mTakeOutService;

    @Inject
    public OnLinePayActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    @Override
    public void getData(Intent intent) {
        String orderId = intent.getStringExtra(OnLinePayActivityPst.EXTRA_ORDER_ID);
        getOrderPayInfo(orderId);
    }

    private void getOrderPayInfo(String orderId) {
        mTakeOutService.getOrderPayInfo(orderId)
                .compose(RxUtils.<RootNode>getSchedulerTransformer())
                .subscribe(new RequestCallback<RootNode>(this) {
                    @Override
                    public void onNext(@NonNull RootNode rootNode) {
                        super.onNext(rootNode);
                    }
                });
    }
}
