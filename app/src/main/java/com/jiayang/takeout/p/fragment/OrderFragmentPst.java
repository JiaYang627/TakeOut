package com.jiayang.takeout.p.fragment;


import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IorderFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-10 15:05.
 */

public class OrderFragmentPst extends BasePresenter<IorderFragmentView> {


    private TakeOutService mTakeOutService;

    @Inject
    public OrderFragmentPst(ErrorListener errorListener , TakeOutService mTakeOutService) {
        super(errorListener);
        this.mTakeOutService = mTakeOutService;
    }
}
