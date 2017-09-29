package com.jiayang.takeout.m.bean.orderFrgVo;

import com.alibaba.fastjson.annotation.JSONField;
import com.jiayang.takeout.m.bean.homeVo.Seller;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-29 10:29.
 */

public class OrderInfoVo {
    @JSONField(name = "id")
    public String id;
    @JSONField(name = "type")
    public String type;
    @JSONField(name = "rider")
    public Rider rider;
    @JSONField(name = "seller")
    public Seller seller;
    @JSONField(name = "goodsInfos")
    public List<GoodsInfo> goodsInfos;
    @JSONField(name = "distribution")
    public Distribution distribution;
    @JSONField(name = "detail")
    public OrderDetail detail;
}
