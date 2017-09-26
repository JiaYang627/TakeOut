package com.jiayang.takeout.m.bean.homeVo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-14 09:50.
 */

public class Head {

    @JSONField(name = "promotionList")
    public List<Promotion> promotionList;
    public List<Category> categorieList;
}
