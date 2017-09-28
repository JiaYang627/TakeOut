package com.jiayang.takeout.v.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by 张 奎 on 2017-08-31 16:56.
 */

public abstract class BaseFragment <T extends BasePresenter> extends AppCompatDialogFragment implements IBaseView{

    @Inject
    protected T mPresenter;
    protected boolean presenterFactoryPrepared;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(((TUApp)getActivity().getApplication()).getApiComponent());
        mPresenter.attachView(this);


    }
    protected abstract void inject(ApiComponent apiComponent);

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getContext(getActivity());
        mPresenter.getData(getActivity().getIntent());
        mPresenter.getArguments(getArguments());
        mPresenter.onTakeView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mPresenter.onHiddenChanged(hidden);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode ,resultCode ,data);
    }
}
