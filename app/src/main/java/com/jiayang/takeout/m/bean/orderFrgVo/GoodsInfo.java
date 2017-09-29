package com.jiayang.takeout.m.bean.orderFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-29 10:32.
 */

public class GoodsInfo {

    @JSONField(name = "id")
    public int id;//商品id
    @JSONField(name = "name")
    public String name;//商品名称
    @JSONField(name = "icon")
    public String icon;//商品图片
    @JSONField(name = "form")
    public String form;//组成
    @JSONField(name = "monthSaleNum")
    public int monthSaleNum;//月销售量
    @JSONField(name = "bargainPrice")
    public boolean bargainPrice;//特价
    @JSONField(name = "isNew")
    public boolean isNew;//是否是新产品
    @JSONField(name = "newPrice")
    public float newPrice;//新价
    @JSONField(name = "oldPrice")
    public int oldPrice;//原价public



}
