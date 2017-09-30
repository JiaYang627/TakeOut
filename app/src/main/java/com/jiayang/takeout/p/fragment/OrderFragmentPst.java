package com.jiayang.takeout.p.fragment;


import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.bean.orderFrgVo.OrderInfoVo;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.utils.userhelper.UserHelper;
import com.jiayang.takeout.v.iview.IorderFragmentView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by 张 奎 on 2017-09-10 15:05.
 */

public class OrderFragmentPst extends BasePresenter<IorderFragmentView> {


    private TakeOutService mTakeOutService;

    @Inject
    public OrderFragmentPst(ErrorListener errorListener, TakeOutService mTakeOutService) {
        super(errorListener);
        this.mTakeOutService = mTakeOutService;
    }



    @Override
    public void onOnceTakeView() {
        super.onOnceTakeView();
        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        if (userBean != null) {
            mTakeOutService.getOrder(userBean._id)
                    .compose(RxUtils.<RootNode>getSchedulerTransformer())
                    .subscribe(new RequestCallback<RootNode>(this) {
                        @Override
                        public void onNext(@NonNull RootNode rootNode) {
                            super.onNext(rootNode);
                            String data = rootNode.data;
                            List<OrderInfoVo> orderInfoVos = JSON.parseArray(data, OrderInfoVo.class);
                            mView.fillData(orderInfoVos);
                        }
                    });
        } else {
            ToastUtils.initToast(context.getString(R.string.no_login));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (!Constants.TEST_PUSH) {
                String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
                UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
                if (userBean != null) {
                    mTakeOutService.getOrder(userBean._id)
                            .compose(RxUtils.<RootNode>getSchedulerTransformer())
                            .subscribe(new RequestCallback<RootNode>(this) {
                                @Override
                                public void onNext(@NonNull RootNode rootNode) {
                                    super.onNext(rootNode);
                                    String data = rootNode.data;
                                    List<OrderInfoVo> orderInfoVos = JSON.parseArray(data, OrderInfoVo.class);
                                    mView.fillData(orderInfoVos);
                                }
                            });
                } else {
                    ToastUtils.initToast(context.getString(R.string.no_login));
                }
            }

        }
    }
}
