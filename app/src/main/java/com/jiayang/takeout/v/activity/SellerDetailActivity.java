package com.jiayang.takeout.v.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.SellerDetailActivityPst;
import com.jiayang.takeout.p.fragment.GoodsFragmentPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.adapter.CommonFragmentAdapter;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.base.LazyFragment;
import com.jiayang.takeout.v.fragment.GoodsFragment;
import com.jiayang.takeout.v.fragment.RecommendFragment;
import com.jiayang.takeout.v.fragment.SellerFragment;
import com.jiayang.takeout.v.iview.IsellerDetailActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 店家详情Act
 */

public class SellerDetailActivity extends BaseActivity<SellerDetailActivityPst> implements IsellerDetailActivityView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.sellerDetailContainer)
    FrameLayout mSellerDetailContainer;

    private List<LazyFragment> mFragments ;
    private String[] mTitles;
    private CommonFragmentAdapter mCommonFragmentAdapter;
    private long id;
    private GoodsFragment mGoodsFragment;

    @Override

    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sellerdetail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragments();
        initShow();
    }

    @Override
    public void initToolBar(String name, long id) {
        this.id = id;
        mToolbar.setTitle(name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);

    }


    private void initFragments() {
        mFragments = new ArrayList<>();
        if (mGoodsFragment == null) {
            mGoodsFragment = new GoodsFragment();
            Bundle bundle = new Bundle();
            bundle.putLong(GoodsFragmentPst.ARGUMENTS_SELLERID_ID, id);
            mGoodsFragment.setArguments(bundle);
        }

        mFragments.add(mGoodsFragment);
        mFragments.add(new SellerFragment());
        mFragments.add(new RecommendFragment());
        mTitles = new String[]{Constants.SELLER_GOODS, Constants.SELLER_SELLER, Constants.SELLER_RECOMMEND};


    }

    private void initShow() {
        mCommonFragmentAdapter = new CommonFragmentAdapter(this.getSupportFragmentManager(), mFragments, mTitles);
        mVp.setAdapter(mCommonFragmentAdapter);
        mVp.setOffscreenPageLimit(2);
        mVp.setCurrentItem(0);
        mTabs.setupWithViewPager(mVp);
    }

}
