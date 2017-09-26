package com.jiayang.takeout.m.bean.goodsFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-19 15:38.
 */

public class GoodsTypeVo {

    @JSONField(name = "id")
    public int id;//商品类型id
    @JSONField(name = "name")
    public String name;//商品类型名称
    @JSONField(name = "info")
    public String info;//特价信息
    @JSONField(name = "list")
    public List<GoodsInfoVo> list;//商品列表

    // 点击某头时，需要知道其分组容器中对应组元素中第一条的下标
    public int groupFirstIndex;
}
