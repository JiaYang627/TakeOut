package com.jiayang.takeout.p.fragment;


import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.ImoreFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-10 15:16.
 */

public class MoreFragmentPst extends BasePresenter<ImoreFragmentView> {


    private TakeOutService mTakeOutService;

    @Inject
    public MoreFragmentPst(ErrorListener errorListener , TakeOutService mTakeOutService) {
        super(errorListener);
        this.mTakeOutService = mTakeOutService;
    }
}
