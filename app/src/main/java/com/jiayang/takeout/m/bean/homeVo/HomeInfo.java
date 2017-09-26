package com.jiayang.takeout.m.bean.homeVo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-14 09:49.
 */

public class HomeInfo {

    @JSONField(name = "head")
    public Head head;
    @JSONField(name = "body")
    public List<HomeItem> body;
}
