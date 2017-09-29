package com.jiayang.takeout.m.bean.orderFrgVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-29 10:31.
 */

public class Distribution {
    // 配送方式
    @JSONField(name = "type")
    public String type;
    // 配送说明
    @JSONField(name = "des")
    public String des;
}
