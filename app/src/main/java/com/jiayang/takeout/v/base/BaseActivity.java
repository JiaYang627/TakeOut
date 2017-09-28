package com.jiayang.takeout.v.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.DBHelper;
import com.jiayang.takeout.p.base.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public abstract class BaseActivity <T extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected Context context;
    private Unbinder unbinder;

    @Inject
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        inject(((TUApp)getApplication()).getApiComponent());
        mPresenter.attachView(this);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mPresenter.setDBHelper(DBHelper.getInstance());
        mPresenter.getContext(context);             //此方法是给P 传递 当前Act的上下文 必须写在getData前面
        mPresenter.getData(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onTakeView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void inject(ApiComponent apiComponent);
    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
    }
}
