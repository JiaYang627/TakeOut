package com.jiayang.takeout.common;

import android.content.Context;
import android.content.Intent;

import com.jiayang.takeout.p.activity.SellerDetailActivityPst;
import com.jiayang.takeout.utils.userhelper.UserHelper;
import com.jiayang.takeout.v.activity.LoginActivity;
import com.jiayang.takeout.v.activity.SellerDetailActivity;
import com.jiayang.takeout.v.activity.SettleActivity;
import com.jiayang.takeout.v.activity.ShopCarActivity;
import com.jiayang.takeout.v.activity.TestActivity;

/**
 * Created by 张 奎 on 2017-09-11 15:13.
 */

public class TakeOutNavigete {

    private boolean isLogin() {
        return UserHelper.isLogin();
    }

    public void goToSellerDetail(Context context, long id, String name) {
        Intent intent = new Intent(context, SellerDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(SellerDetailActivityPst.EXTRA_SELLERID_ID,id);
        intent.putExtra(SellerDetailActivityPst.EXTRA_SELLERNAME_ID,name);
        context.startActivity(intent);
    }

    public void goToTest(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }

    public void goToShopCar(Context context) {
        Intent intent = new Intent(context, ShopCarActivity.class);
        context.startActivity(intent);
    }

    public void goToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void goToSettle(Context context) {
        Intent intent = new Intent();
        if (isLogin()) {
            intent.setClass(context, SettleActivity.class);
        } else {
            intent.setClass(context, LoginActivity.class);
        }
        context.startActivity(intent);
    }


}
