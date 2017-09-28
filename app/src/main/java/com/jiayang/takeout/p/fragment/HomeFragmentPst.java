package com.jiayang.takeout.p.fragment;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.LocationBean;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.v.activity.LocationActivity;
import com.jiayang.takeout.v.iview.IhomeFragmentView;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static android.R.attr.data;

/**
 * Created by 张 奎 on 2017-09-09 09:33.
 */

public class HomeFragmentPst  extends BasePresenter<IhomeFragmentView> {

    private TakeOutService mTakeOutService;

    @Inject
    public HomeFragmentPst(ErrorListener errorListener , TakeOutService mTakeOutService) {
        super(errorListener);
        this.mTakeOutService = mTakeOutService;
    }

    @Override
    public void onTakeView() {
        super.onTakeView();


    }

    @Override
    public void onOnceTakeView() {
        super.onOnceTakeView();

        mTakeOutService.home()
                .compose(RxUtils.<RootNode>getSchedulerTransformer())
                .subscribe(new RequestCallback<RootNode>(this) {
                    @Override
                    public void onNext(@NonNull RootNode rootNode) {
                        super.onNext(rootNode);

                        if ("0".equals(rootNode.code)) {
                            String data = rootNode.data;
                            HomeInfo homeInfo = JSON.parseObject(data , HomeInfo.class);
                            mView.fillData(homeInfo);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {

        }
    }

    public void goToLocation() {
        mTakeOutNavigate.goToLocation(context);
    }


}
