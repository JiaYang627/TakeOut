package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.services.core.LatLonPoint;
import com.bumptech.glide.Glide;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsInfoVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.activity.SettleActivityPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.NumberFormatUtils;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IsettleActivityView;
import com.jiayang.takeout.widget.ShoppingCartManager;
import com.jiayang.takeout.widget.imageLoader.ImageLoader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 结算中心Act
 */

public class SettleActivity extends BaseActivity<SettleActivityPst> implements IsettleActivityView {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_selected_address_container)
    LinearLayout mLlSelectedAddressContainer;
    @BindView(R.id.tv_select_address)
    TextView mTvSelectAddress;
    @BindView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @BindView(R.id.ll_select_goods)
    LinearLayout mLlSelectGoods;
    @BindView(R.id.tv_send_price)
    TextView mTvSendPrice;
    @BindView(R.id.tv_count_price)
    TextView mTvCountPrice;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settle;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }

    private void initData() {
        if (Constants.Location.LOCATION != null) {
            String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
            UserBean mUserBean = JSON.parseObject(userInfo, UserBean.class);
            mPresenter.findAllByUserId(mUserBean._id);
        }

        ImageLoader.loadImageViewFromUrl(ShoppingCartManager.getInstance().url.replace("Takeout", "takeout"), mIvLogo);
        mTvSellerName.setText(ShoppingCartManager.getInstance().name);


        CopyOnWriteArrayList<GoodsInfoVo> goodsInfos = ShoppingCartManager.getInstance().goodsInfos;
        for (GoodsInfoVo item : goodsInfos) {
            View view = View.inflate(context, R.layout.item_settle_center_goods, null);

            ((TextView) view.findViewById(R.id.tv_name)).setText(item.name);
            ((TextView) view.findViewById(R.id.tv_count)).setText("X" + item.count);
            ((TextView) view.findViewById(R.id.tv_price)).setText("¥" + item.newPrice);

            //View view 的高 30dp 加载不到 通过系统自己的类 将dp转换为px
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            mLlSelectGoods.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, height);

        }

        // 配送费
        mTvSendPrice.setText(NumberFormatUtils.formatDigits(ShoppingCartManager.getInstance().sendPrice));
        // 待支付

        double countPrice = ShoppingCartManager.getInstance().getMoney() / 100.0F + ShoppingCartManager.getInstance().sendPrice;
        mTvCountPrice.setText(getString(R.string.order_no_pay) + NumberFormatUtils.formatDigits(countPrice));


    }

    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                this.finish();
                break;
            case R.id.rl_location:
                // 跳转地址页面
                mPresenter.goToAddress();
                break;
            case R.id.tv_submit:
                mPresenter.goToCreatOrder();
                break;
        }
    }

    @Override
    public void fillData(Object object) {
        if (object instanceof AddressBean) {
            AddressBean addressBean = (AddressBean) object;
            mTvSelectAddress.setVisibility(View.GONE);
            mLlSelectedAddressContainer.setVisibility(View.VISIBLE);

            mTvName.setText(addressBean.name);
            mTvSex.setText(addressBean.sex);
            mTvPhone.setText(addressBean.phone);
            mTvAddress.setText(addressBean.receiptAddress + addressBean.detailAddress);
        }

        if (object instanceof List) {
            // 判断定位点与地址列表中那个记录的距离近（小于500米），将该地址信息作为默认地址
            List<AddressBean> been = (List<AddressBean>) object;
            for (AddressBean item : been) {
                LatLonPoint point = new LatLonPoint(item.latitude, item.longitude);
                double distance = getDistance(Constants.Location.LOCATION, point);
                if (distance < 500) {
                    // 该条目为默认地址
                    // 修改界面
                    mTvSelectAddress.setVisibility(View.GONE);
                    mLlSelectedAddressContainer.setVisibility(View.VISIBLE);


                    mTvName.setText(item.name);
                    mTvSex.setText(item.sex);
                    mTvPhone.setText(item.phone);
                    mTvAddress.setText(item.receiptAddress + item.detailAddress);
                    mPresenter.setAddressId(item._id);
                }
            }
        }
    }

    /*
    * 计算两点之间距离
    *
    * @param start
    *
    * @param end
    *
    * @return 米
    */
    public double getDistance(LatLonPoint start, LatLonPoint end) {

        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.getLongitude();
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.getLatitude();

        // 地球半径
        double R = 6371;

        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        return d * 1000;
    }
}
