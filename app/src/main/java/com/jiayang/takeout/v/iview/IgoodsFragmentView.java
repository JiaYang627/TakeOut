package com.jiayang.takeout.v.iview;

import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsTypeVo;
import com.jiayang.takeout.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-17 08:33.
 */

public interface IgoodsFragmentView extends IBaseView {
    void fillData(List<GoodsTypeVo> goodsTypeVos);
}
