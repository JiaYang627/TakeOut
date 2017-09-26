package com.jiayang.takeout.m.bean.homeVo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-14 09:54.
 */

public class HomeItem {

    @JSONField(name = "type")
    public int type;
    @JSONField(name = "seller")
    public Seller seller;// 商家
    @JSONField(name = "recommendInfos")
    public List<String> recommendInfos;// 大家都在找
}
