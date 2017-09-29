package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.fragment.UserFragmentPst;
import com.jiayang.takeout.utils.PreferenceTool;
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
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
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

        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        if (userBean != null) {

            //立即登录 隐藏
            mLogin.setVisibility(View.GONE);

            mUsername.setText(userBean.name);
            mPhone.setText(userBean.phone.substring(0, 3) + "****" + userBean.phone.substring(7, 11));
            mLlUserinfo.setVisibility(View.VISIBLE);

        } else {
            mLogin.setVisibility(View.VISIBLE);
            mLlUserinfo.setVisibility(View.GONE);
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
