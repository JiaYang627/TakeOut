package com.jiayang.takeout.p.fragment;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsTypeVo;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.p.base.LazyPresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.iview.IgoodsFragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by 张 奎 on 2017-09-17 08:33.
 */

public class GoodsFragmentPst extends LazyPresenter<IgoodsFragmentView> {


    public static final String ARGUMENTS_SELLERID_ID = "sellerId";
    private TakeOutService mTakeOutService;
    private long mSellerId;
    private boolean isFirst =true;

    @Inject
    public GoodsFragmentPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    @Override
    public void getArguments(Bundle bundle) {
        mSellerId = bundle.getLong(GoodsFragmentPst.ARGUMENTS_SELLERID_ID);
    }


    @Override
    protected void lazyLoad() {
        mTakeOutService.getGoodsInfo(mSellerId)
                .compose(RxUtils.<RootNode>getSchedulerTransformer())
                .subscribe(new RequestCallback<RootNode>(this) {
                    @Override
                    public void onNext(@NonNull RootNode rootNode) {
                        super.onNext(rootNode);

                        if ("0".equals(rootNode.code)) {
                            String data = rootNode.data;

//                            ArrayList<GoodsTypeVo> goodsTypeVos = JSONArray.parseObject(data, new TypeReference<ArrayList<GoodsTypeVo>>() {
//                            });
                            List<GoodsTypeVo> goodsTypeVos = JSONArray.parseArray(data, GoodsTypeVo.class);

                            mView.fillData(goodsTypeVos);
                        }
                    }
                });
    }


    public void goToShopCar() {
        mTakeOutNavigete.goToShopCar(context);
    }
}
