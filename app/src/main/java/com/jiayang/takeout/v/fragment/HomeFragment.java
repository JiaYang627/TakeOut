package com.jiayang.takeout.v.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.HomeFragmentPst;
import com.jiayang.takeout.v.adapter.HomeRecyclerViewAdapter;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.IhomeFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页Frg
 */

public class HomeFragment extends BaseFragment<HomeFragmentPst> implements IhomeFragmentView, AMapLocationListener {
    @BindView(R.id.homeRecyclerView)
    RecyclerView mHomeRecyclerView;
    @BindView(R.id.homeTextAddress)
    TextView mHomeTextAddress;
    @BindView(R.id.homeTopLayoutSearch)
    LinearLayout mHomeTopLayoutSearch;
    @BindView(R.id.homeTopLayout)
    LinearLayout mHomeTopLayout;
    private Unbinder mUnbinder;
    private View mHeadView;
    private HomeRecyclerViewAdapter mAdapter;
    private int scrollY = 0;//记录recycler滑动Y值
    private float duration = 150.0f;//此处设定 0 - 150 之间去改变头部的透明度
    private ArgbEvaluator mArgbEvaluator;
    private boolean isFirst = true;
    private AMapLocationClient mMlocationClient;


    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.home_frg, null);
        mUnbinder = ButterKnife.bind(this, view);
        initHeadView();//初始化headView
        initAdapter();
        initRecycler();
        initListener();
        initLocation();
        mArgbEvaluator = new ArgbEvaluator();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null && !isFirst) {
            mAdapter.notifyDataSetChanged();
//            LogUtils.e("HomeFragment ===============================onResume");
        }
        isFirst = false;


        if (Constants.Location.isChange) {
            mHomeTextAddress.setText(Constants.Location.TITLE);

            Constants.Location.isChange = false;
            Constants.Location.TITLE = "";
            Constants.Location.SNIPPET = "";
            Constants.Location.LONGITUDE = 0;
            Constants.Location.LATITUDE = 0;
        }
    }

    private void initLocation() {
        mMlocationClient = new AMapLocationClient(this.getContext());
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mMlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mMlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mMlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            Constants.Location.LOCATION = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());

            String address = aMapLocation.getAddress();

            mHomeTextAddress.setText(address);
            mMlocationClient.stopLocation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initHeadView() {
//        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.home_frg_head, null);


    }

    private void initAdapter() {
        mAdapter = new HomeRecyclerViewAdapter();

    }

    private void initRecycler() {
        mHomeRecyclerView.setAdapter(mAdapter);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initListener() {
        mHomeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollY += dy;
                int bgColor = 0X993190E8;
                if (scrollY < 0) {
                    bgColor = 0X993190E8;
                } else if (scrollY > 150) {
                    bgColor = 0XFF3190E8;
                } else {
                    bgColor = (int) mArgbEvaluator.evaluate(scrollY / duration, 0X993190E8, 0XFF3190E8);
                }


                mHomeTopLayout.setBackgroundColor(bgColor);

            }
        });
    }

    @Override
    public void fillData(HomeInfo homeInfo) {
        mAdapter.setData(homeInfo);
    }

    @OnClick(R.id.homeTextAddress)
    public void onViewClicked() {
        mPresenter.goToLocation();

    }

}
