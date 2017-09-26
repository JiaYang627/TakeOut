package com.jiayang.takeout.p.fragment;



import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.v.iview.IuserFragmentView;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-09-09 09:55.
 */

public class UserFragmentPst extends BasePresenter<IuserFragmentView> {


    private TakeOutService mTakeOutService;

    @Inject
    public UserFragmentPst(ErrorListener errorListener , TakeOutService mTakeOutService) {
        super(errorListener);
        this.mTakeOutService = mTakeOutService;
    }

    public void goToLogin() {
        mTakeOutNavigete.goToLogin(context);
    }
}
