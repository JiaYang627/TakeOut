package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.bean.orderFrgVo.OrderInfoVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.OrderFragmentPst;
import com.jiayang.takeout.v.adapter.OrderRecyclerViewAdapter;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.IorderFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-09-10 15:04.
 */

public class OrderFragment extends BaseFragment<OrderFragmentPst> implements IorderFragmentView {

    @BindView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    private Unbinder mUnbinder;
    private OrderRecyclerViewAdapter mAdapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_frg, null);
        mUnbinder = ButterKnife.bind(this, view);
        initRecycler();

        return view;

    }

    private void initRecycler() {
        mAdapter = new OrderRecyclerViewAdapter();
        mRvOrderList.setAdapter(mAdapter);
        mRvOrderList.setLayoutManager(new LinearLayoutManager(this.getContext() ,LinearLayoutManager.VERTICAL ,false));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void fillData(List<OrderInfoVo> orderInfoVos) {
        mAdapter.setOrders(orderInfoVos);
    }
}
