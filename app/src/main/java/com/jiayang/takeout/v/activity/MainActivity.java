package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.MainActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.fragment.HomeFragment;
import com.jiayang.takeout.v.fragment.MoreFragment;
import com.jiayang.takeout.v.fragment.OrderFragment;
import com.jiayang.takeout.v.fragment.UserFragment;
import com.jiayang.takeout.v.iview.ImainAcitivityView;
import com.jiayang.takeout.widget.TabFragmentHost;

import butterknife.BindView;


public class MainActivity extends BaseActivity<MainActivityPst> implements ImainAcitivityView {


    @BindView(android.R.id.tabcontent)
    FrameLayout mTabcontent;
    @BindView(android.R.id.tabs)
    TabWidget mTabs;
    @BindView(R.id.tabFragmentHost)
    TabFragmentHost mTabFragmentHost;

    private int[] tabImgRes;
    private Class[] fragments;
    private String[] tabTags;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRes();
        initView();
    }

    private void initRes() {

        tabImgRes = new int[]{R.drawable.selector_icon_main_home,
                R.drawable.selector_icon_main_order,
                R.drawable.selector_icon_main_me,
                R.drawable.selector_icon_main_more};

        fragments = new Class[]{HomeFragment.class, OrderFragment.class, UserFragment.class, MoreFragment.class};
        tabTags = new String[]{Constants.MAIN_HOME , Constants.MAIN_ORDER , Constants.MAIN_USER , Constants.MAIN_MORE};

    }

    private void initView() {
        mTabFragmentHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        Bundle bundle = new Bundle();
        for (int i = 0; i < fragments.length; i++) {
            mTabFragmentHost.addTab(mTabFragmentHost.newTabSpec(tabTags[i]).setIndicator(getTab(i)), fragments[i], bundle);
        }
        mTabFragmentHost.getTabWidget().setDividerDrawable(null);
    }
    private View getTab(int i) {
        View view = getLayoutInflater().inflate(R.layout.item_main_tab, null);
        TextView mainTextView = (TextView) view.findViewById(R.id.mainTextView);
        mainTextView.setText(tabTags[i]);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        img.setImageResource(tabImgRes[i]);

        return view;
    }
}
