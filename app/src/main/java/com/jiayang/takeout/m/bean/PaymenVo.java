package com.jiayang.takeout.m.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-27 16:39.
 */

public class PaymenVo {


    @JSONField(name = "id")
    public int id;
    @JSONField(name = "name")
    public String name;
    @JSONField(name = "url")
    public String url;
}
