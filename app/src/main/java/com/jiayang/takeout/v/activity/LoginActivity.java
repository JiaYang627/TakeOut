package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.LoginActivityPst;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IloginActivityView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录Act
 */

public class LoginActivity extends BaseActivity<LoginActivityPst> implements IloginActivityView {
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.login)
    TextView mLogin;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        String userPhone = mEtUserPhone.getText().toString();
        mPresenter.goToLogin(userPhone);
    }

    @Override
    public void success() {
        ToastUtils.initToast(getString(R.string.user_login_success));
        this.finish();
    }
}
