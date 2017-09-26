package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.UserFragmentPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.iview.IuserFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 张 奎 on 2017-09-09 09:54.
 */

public class UserFragment extends BaseFragment<UserFragmentPst> implements IuserFragmentView {

    @BindView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @BindView(R.id.login)
    ImageView mLogin;
    private Unbinder mUnbinder;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_frg, null);
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

    @OnClick({R.id.tv_user_setting, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_setting:
                break;
            case R.id.login:
                mPresenter.goToLogin();
                break;
        }
    }
}
