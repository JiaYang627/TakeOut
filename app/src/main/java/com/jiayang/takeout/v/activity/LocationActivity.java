package com.jiayang.takeout.v.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.LocationActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IlocationActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 定位Act
 */

public class LocationActivity extends BaseActivity<LocationActivityPst> implements IlocationActivityView, LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.locationRecyclerView)
    RecyclerView mLocationRecyclerView;
    @BindView(R.id.activity_select_location)
    FrameLayout mActivitySelectLocation;

    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private PoiSearch.Query mQuery;
    private List<PoiItem> poiItems;// poi数据
    private MyAdapter adapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location;
    }

    // 定位操作：
    // 1.MapView
    // 2.MapView的管理工具获取
    // 3.基本设置
    // 4.配置小蓝点
    // 5.设置监听（定位、定位结果通知）
    // 6.开始定位
    // 7.获取位置信息
    // 8.结束定位

    // 周边搜索：
    // 1.获取定位点，开启周边搜索（指定范围和搜索内容）
    // 2.结果处理（兴趣点列表）

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMap.onCreate(savedInstanceState);// 此方法必须重写
        mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (aMap == null) {
            aMap = mMap.getMap();
            setUpMap();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMap != null) {
            mMap.onDestroy();
        }
    }


    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));// 设置可放大级别 最大级别 19


        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false


    }


    // 开始定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }
    // 获取位置信息
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点


//                // 停止定位
                mlocationClient.stopLocation();
//                // 查询附近信息
//
                Constants.Location.LOCATION= new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                doSearchQuery(aMapLocation.getCity());


            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    protected void doSearchQuery(String city) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        mQuery = new PoiSearch.Query("", "", city);
        mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(0);// 设置查第一页

        if (Constants.Location.LOCATION != null) {
            PoiSearch poiSearch = new PoiSearch(this, mQuery);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(Constants.Location.LOCATION, 500, true));//
            // 设置搜索区域为以 Constants.Location.LOCATION 点为圆心，其周围500米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    // 结束定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(mQuery)) {// 是否是同一条
                    poiItems = result.getPois();
                    adapter = new MyAdapter();
                    mLocationRecyclerView.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(LocationActivity.this).inflate(R.layout.item_location_address, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            PoiItem poiItem = poiItems.get(position);
            viewHolder.setData(poiItem);
        }

        @Override
        public int getItemCount() {
            return poiItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.tv_title)
            TextView tvTitle;
            @BindView(R.id.tv_snippet)
            TextView tvSnippet;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
            PoiItem data;
            public void setData(PoiItem item){
                this.data=item;
                tvTitle.setText(item.getTitle());
                tvSnippet.setText(item.getSnippet());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent();
//                        intent.putExtra("title",data.getTitle());
//                        intent.putExtra("snippet",data.getSnippet());
//
//                        LatLonPoint point = data.getLatLonPoint();
//
//                        intent.putExtra("latitude",point.getLatitude());
//                        intent.putExtra("longitude", point.getLongitude());
//
//
//                        SelectLocationActivity.this.setResult(200,intent);
//                        SelectLocationActivity.this.finish();
//                        mPresenter.setResult();


                        Constants.Location.isChange = true;
                        Constants.Location.TITLE = data.getTitle();
                        Constants.Location.SNIPPET = data.getSnippet();
                        LatLonPoint point = data.getLatLonPoint();
                        Constants.Location.LATITUDE = point.getLatitude();
                        Constants.Location.LONGITUDE = point.getLongitude();
                        LocationActivity.this.finish();

                    }
                });
            }


        }
    }
}
