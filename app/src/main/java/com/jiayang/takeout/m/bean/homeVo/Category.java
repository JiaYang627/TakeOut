package com.jiayang.takeout.m.bean.homeVo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 张 奎 on 2017-09-14 09:53.
 */

public class Category {

    @JSONField(name = "id")
    public int id;
    @JSONField(name = "pic")
    public String pic;
    @JSONField(name = "name")
    public String name;
}
