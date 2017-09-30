package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.OrderDetailActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IorderDetailActivityView;

import butterknife.BindView;

/**
 * 订单详情Act
 */

public class OrderDetailActivity extends BaseActivity<OrderDetailActivityPst> implements IorderDetailActivityView {

    @BindView(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @BindView(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @BindView(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;
    private String mOrderId;
    private String mType;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrderId = getIntent().getStringExtra("orderId");
        mType = getIntent().getStringExtra("type");

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi(mType);
    }

    private void updateUi(String type) {
        int index = getIndex(type);

        for (int i = 0; i < mLlOrderDetailTypeContainer.getChildCount(); i++) {
            TextView textView = (TextView) mLlOrderDetailTypeContainer.getChildAt(i);
            ImageView imageView = (ImageView) mLlOrderDetailTypePointContainer.getChildAt(i);
            textView.setTextColor(getResources().getColor(R.color.grayTextColor));
            imageView.setImageResource(R.drawable.order_time_node_normal);
        }


        if (index != -1) {
            TextView textView = (TextView) mLlOrderDetailTypeContainer.getChildAt(index);
            ImageView imageView = (ImageView) mLlOrderDetailTypePointContainer.getChildAt(index);

            textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            imageView.setImageResource(R.drawable.order_time_node_do);
        }

    }

    private int getIndex(String type) {
        int index = -1;

        switch (type) {
            case Constants.OrderObserver.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                break;
            case Constants.OrderObserver.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case Constants.OrderObserver.ORDERTYPE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case Constants.OrderObserver.ORDERTYPE_DISTRIBUTION:
//                typeInfo = "配送中";
                index = 2;
                break;
            case Constants.OrderObserver.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case Constants.OrderObserver.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }
        return index;
    }
}
