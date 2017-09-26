package com.jiayang.takeout.m.bean.goodsFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-19 15:40.
 */

public class GoodsInfoVo {
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
    @JSONField(name ="bargainPrice" )
    public boolean bargainPrice;//特价
    @JSONField(name = "isNew")
    public boolean isNew;//是否是新产品
    @JSONField(name = "newPrice")
    public float newPrice;//新价
    @JSONField(name = "oldPrice")
    public int oldPrice;//原价public


    public int headId;// 进行分组操作，同组数据该字段值相同
    public int headIndex;  // 当前条目对应头数据所在集合的下标

    public int count;// 用于购物数量统计
}
