package com.jiayang.takeout.m.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-27 16:37.
 */

public class OrderPayInfoVo {

    @JSONField(name = "payDownTime")
    public int payDownTime;
    @JSONField(name = "money")
    public double money;
    @JSONField(name = "paymentInfo")
    public List<PaymenVo> paymentInfo;
}
