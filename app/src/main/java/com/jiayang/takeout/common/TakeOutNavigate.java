package com.jiayang.takeout.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jiayang.takeout.p.activity.OnLinePayActivityPst;
import com.jiayang.takeout.p.activity.SellerDetailActivityPst;
import com.jiayang.takeout.p.activity.SettleActivityPst;
import com.jiayang.takeout.utils.userhelper.UserHelper;
import com.jiayang.takeout.v.activity.EditAddressActivity;
import com.jiayang.takeout.v.activity.LocationActivity;
import com.jiayang.takeout.v.activity.LoginActivity;
import com.jiayang.takeout.v.activity.OnLinePayActivity;
import com.jiayang.takeout.v.activity.ReceiptActivity;
import com.jiayang.takeout.v.activity.SellerDetailActivity;
import com.jiayang.takeout.v.activity.SettleActivity;
import com.jiayang.takeout.v.activity.ShopCarActivity;
import com.jiayang.takeout.v.activity.TestActivity;

/**
 * Created by 张 奎 on 2017-09-11 15:13.
 */

public class TakeOutNavigate {

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


    public void goToAddress(Context context) {
        Intent intent = new Intent(context, ReceiptActivity.class);
        ((Activity)context).startActivityForResult(intent , 200);
    }

    public void goToEditAddress(Context context , int id) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public void goToSettleForResult(Context context, int id) {
        Intent intent = new Intent();
        intent.putExtra(SettleActivityPst.EXTRA_ADDRESS_ID, id);
        ((Activity) context).setResult(Activity.RESULT_OK, intent);
        ((Activity) context).finish();
    }

    public void goToOnlinePay(Context context, String orderId) {
        Intent intent = new Intent(context, OnLinePayActivity.class);
        intent.putExtra(OnLinePayActivityPst.EXTRA_ORDER_ID, orderId);
        context.startActivity(intent);
    }

    public void goToLocation(Context context) {
        Intent intent = new Intent(context, LocationActivity.class);
        context.startActivity(intent);
    }
}
