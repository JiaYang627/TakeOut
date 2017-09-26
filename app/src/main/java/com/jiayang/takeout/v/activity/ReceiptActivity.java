package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.p.activity.ReceiptActivityPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IreceiptActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 张 奎 on 2017-09-26 11:28.
 */

public class ReceiptActivity extends BaseActivity<ReceiptActivityPst> implements IreceiptActivityView {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt_address;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                this.finish();
                break;
            case R.id.tv_add_address:
                break;
        }
    }

    @Override
    public void fillData(List<AddressBean> addressBeen) {
        LogUtils.e("ReceiptActivity=====================================:"+ addressBeen.size());
    }
}
