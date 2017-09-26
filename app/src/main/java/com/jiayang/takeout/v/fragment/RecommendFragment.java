package com.jiayang.takeout.v.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.fragment.RecommendFragmentPst;
import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.base.LazyFragment;
import com.jiayang.takeout.v.iview.IrecommendFragmentView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载Frg未做细致处理，如果第一个页面 无需写任何东西，第一次请求只在Pst的onOnceTake中写即可。
 * 其余的需在方法中 做处理 并将变量 致为false。
 */

public class RecommendFragment extends LazyFragment<RecommendFragmentPst> implements IrecommendFragmentView{

    private Unbinder mUnbinder;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.recommend_frg, null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
