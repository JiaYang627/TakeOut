package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.TestActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.ItestActivityView;


public class TestActivity extends BaseActivity<TestActivityPst> implements ItestActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void showA(String b) {
        Toast.makeText(context,  b, Toast.LENGTH_SHORT).show();
    }
}
