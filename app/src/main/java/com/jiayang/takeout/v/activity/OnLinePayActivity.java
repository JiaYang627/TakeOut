package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.OnLinePayActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IonLinePayActivityView;

/**
 * 支付页面
 */

public class OnLinePayActivity extends BaseActivity<OnLinePayActivityPst> implements IonLinePayActivityView{
    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_pay;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
