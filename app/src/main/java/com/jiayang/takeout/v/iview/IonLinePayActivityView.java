package com.jiayang.takeout.v.iview;

import com.jiayang.takeout.m.bean.OrderPayInfoVo;
import com.jiayang.takeout.v.base.IBaseView;

/**
 * Created by 张 奎 on 2017-09-27 15:59.
 */

public interface IonLinePayActivityView extends IBaseView {
    void fillData(OrderPayInfoVo orderPayInfoVo, String orderId);
}
