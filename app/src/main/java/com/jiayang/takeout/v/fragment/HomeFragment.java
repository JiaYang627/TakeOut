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

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.p.fragment.HomeFragmentPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.adapter.HomeRecyclerViewAdapter;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.IhomeFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-09-09 09:32.
 */

public class HomeFragment extends BaseFragment<HomeFragmentPst> implements IhomeFragmentView {
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
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext() , LinearLayoutManager.VERTICAL , false));
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

}
