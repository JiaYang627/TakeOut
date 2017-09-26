package com.jiayang.takeout.v.iview;


import com.jiayang.takeout.m.bean.homeVo.HomeInfo;
import com.jiayang.takeout.v.base.IBaseView;

/**
 * Created by 张 奎 on 2017-09-09 09:34.
 */

public interface IhomeFragmentView extends IBaseView {
    void fillData(HomeInfo homeInfo);
}
