package com.jiayang.takeout.m.component;


import com.jiayang.takeout.m.model.ApiModule;
import com.jiayang.takeout.v.activity.EditAddressActivity;
import com.jiayang.takeout.v.activity.LocationActivity;
import com.jiayang.takeout.v.activity.LoginActivity;
import com.jiayang.takeout.v.activity.MainActivity;
import com.jiayang.takeout.v.activity.OnLinePayActivity;
import com.jiayang.takeout.v.activity.OrderDetailActivity;
import com.jiayang.takeout.v.activity.ReceiptActivity;
import com.jiayang.takeout.v.activity.SellerDetailActivity;
import com.jiayang.takeout.v.activity.SettleActivity;
import com.jiayang.takeout.v.activity.ShopCarActivity;
import com.jiayang.takeout.v.activity.TestActivity;
import com.jiayang.takeout.v.fragment.GoodsFragment;
import com.jiayang.takeout.v.fragment.HomeFragment;
import com.jiayang.takeout.v.fragment.MoreFragment;
import com.jiayang.takeout.v.fragment.OrderFragment;
import com.jiayang.takeout.v.fragment.RecommendFragment;
import com.jiayang.takeout.v.fragment.SellerFragment;
import com.jiayang.takeout.v.fragment.UserFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity mainActivity);

    void inject(TestActivity testActivity);

    void inject(HomeFragment fragment);

    void inject(MoreFragment fragment);

    void inject(UserFragment fragment);

    void inject(OrderFragment fragment);

    void inject(SellerDetailActivity activity);

    void inject(GoodsFragment fragment);

    void inject(SellerFragment fragment);

    void inject(RecommendFragment fragment);

    void inject(ShopCarActivity activity);

    void inject(LoginActivity activity);

    void inject(SettleActivity activity);

    void inject(ReceiptActivity activity);

    void inject(EditAddressActivity activity);

    void inject(OnLinePayActivity activity);

    void inject(LocationActivity activity);

    void inject(OrderDetailActivity activity);
}
