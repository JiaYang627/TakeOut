package com.jiayang.takeout.v.iview;


import com.jiayang.takeout.m.bean.orderFrgVo.OrderInfoVo;
import com.jiayang.takeout.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-10 15:05.
 */

public interface IorderFragmentView extends IBaseView {
    void fillData(List<OrderInfoVo> orderInfoVos);
}
