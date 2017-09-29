package com.jiayang.takeout.m.bean.orderFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-29 10:46.
 */

public class Location {

    @JSONField(name = "latitude")
    public String latitude;
    @JSONField(name = "longitude")
    public String longitude;
}
