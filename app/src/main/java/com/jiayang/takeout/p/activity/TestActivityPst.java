package com.jiayang.takeout.p.activity;

import android.content.Intent;
import android.widget.Toast;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.ItestActivityView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-02 08:31.
 */

public class TestActivityPst extends BasePresenter<ItestActivityView> {


    private TakeOutService takeOutService;

    @Inject
    public TestActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        this.takeOutService = takeOutService;
    }

    @Override
    public void getData(Intent intent) {
        String b = intent.getStringExtra("aaa");
        Toast.makeText(context,  b, Toast.LENGTH_SHORT).show();
//        mView.showA(b);
//        Log.e("TestActivity aaa :", aaa + "");
    }
}
