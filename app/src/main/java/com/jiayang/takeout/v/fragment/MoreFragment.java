package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.MoreFragmentPst;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.utils.userhelper.UserHelper;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.ImoreFragmentView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Main 更多Frg
 */

public class MoreFragment extends BaseFragment<MoreFragmentPst> implements ImoreFragmentView {

    @BindView(R.id.buttonView)
    Button mButtonView;
    private Unbinder mUnbinder;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_frg, null);
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

    public void initData() {
        HashMap<String, String> data = new HashMap<>();
        data.put("type", "20");
        data.put("orderId", "1010 8027 3652 5689 39");
        if (data.size() > 0) {
            Constants.OrderObserver.getOrderObserver().changeOrderInfo(data);
        }
    }
    @OnClick(R.id.buttonView)
    public void onViewClicked() {
        if (UserHelper.isLogin()) {
            initData();
            ToastUtils.initToast(getString(R.string.test_push));
            Constants.TEST_PUSH = true;
        } else {
            ToastUtils.initToast(getString(R.string.no_login));
        }
    }
}
