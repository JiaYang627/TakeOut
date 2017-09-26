package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.OrderFragmentPst;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.IorderFragmentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-09-10 15:04.
 */

public class OrderFragment extends BaseFragment<OrderFragmentPst> implements IorderFragmentView {

    private Unbinder mUnbinder;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_frg, null);
        mUnbinder = ButterKnife.bind(this, view);

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
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
}
