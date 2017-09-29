package com.jiayang.takeout.m.bean.orderFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-29 10:31.
 */

public class OrderDetail {

    @JSONField(name = "username")
    public String username;
    @JSONField(name = "phone")
    public String phone;
    @JSONField(name = "address")
    public String address;
    @JSONField(name = "pay")
    public String pay;
    @JSONField(name = "time")
    public String time;
}
