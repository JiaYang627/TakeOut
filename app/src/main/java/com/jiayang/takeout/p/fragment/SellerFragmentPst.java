package com.jiayang.takeout.p.fragment;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.p.base.LazyPresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.iview.IsellerFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-17 08:40.
 */

public class SellerFragmentPst extends LazyPresenter<IsellerFragmentView> {


    private TakeOutService mTakeOutService;

    @Inject
    public SellerFragmentPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }


    @Override
    protected void lazyLoad() {
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
