package com.jiayang.takeout.m.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-14 09:48.
 */

public class RootNode {
    @JSONField(name = "code")
    public String code;
    @JSONField(name = "data")
    public String data;
}
