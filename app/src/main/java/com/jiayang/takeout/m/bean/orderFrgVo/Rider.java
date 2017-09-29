package com.jiayang.takeout.m.bean.orderFrgVo;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-29 10:31.
 */

public class Rider {

    @JSONField(name = "id")
    public int id;
    @JSONField(name = "name")
    public String name;
    @JSONField(name = "phone")
    public String phone;
    @JSONField(name = "location")
    public Location location;

}
