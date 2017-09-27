package com.jiayang.takeout.p.activity;

import android.app.Activity;
import android.content.Intent;

import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.iview.IsettleActivityView;

import java.sql.SQLException;

import javax.inject.Inject;

import static com.jiayang.takeout.p.activity.ReceiptActivityPst.dao;

/**
 * Created by 张 奎 on 2017-09-24 09:24.
 */

public class SettleActivityPst extends BasePresenter<IsettleActivityView> {

    public static String EXTRA_ADDRESS_ID = "addressId";
    private TakeOutService mTakeOutService;

    @Inject
    public SettleActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    public void goToAddress() {
        mTakeOutNavigete.goToAddress(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 200) {
                if (dao == null) {
                    try {
                        dao = mDBHelper.getDao(AddressBean.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                int id = data.getIntExtra(SettleActivityPst.EXTRA_ADDRESS_ID, -1);
                findById(id);


            }
        }
    }

    public void findById(int id) {
        try {
            AddressBean addressBeen = dao.queryForId(id);
            mView.fillData(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
