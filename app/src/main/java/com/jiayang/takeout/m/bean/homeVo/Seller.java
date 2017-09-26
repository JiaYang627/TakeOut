package com.jiayang.takeout.m.bean.homeVo;

import android.content.pm.ActivityInfo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张 奎 on 2017-09-14 09:55.
 */

public class Seller {

    @JSONField(name = "id")
    public long id;
    @JSONField(name = "pic")
    public String pic;
    @JSONField(name = "name")
    public String name;
    @JSONField(name = "score")
    public String score;
    @JSONField(name = "sale")
    public String sale;
    @JSONField(name = "ensure")
    public String ensure;
    @JSONField(name = "invoice")
    public String invoice;
    @JSONField(name = "sendPrice")
    public int sendPrice;
    @JSONField(name = "deliveryFee")
    public String deliveryFee;
    @JSONField(name = "recentVisit")
    public String recentVisit;
    @JSONField(name = "distance")
    public String distance;
    @JSONField(name = "time")
    public String time;
    @JSONField(name = "activityList")
    public List<ActivityInfo> activityList;
}
