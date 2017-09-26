package com.jiayang.takeout.v.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jiayang.takeout.v.base.BaseFragment;
import com.jiayang.takeout.v.base.LazyFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25.
 */

public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<LazyFragment> fragments;

    public CommonFragmentAdapter(FragmentManager fm, List<LazyFragment> fragments, String[] titles) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
