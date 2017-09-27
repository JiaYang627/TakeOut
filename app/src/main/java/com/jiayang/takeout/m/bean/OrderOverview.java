package com.jiayang.takeout.m.bean;

import java.util.List;

/**
 * 订单的概要信息
 */

public class OrderOverview {
    public int addressId;
    public int userId;
    public List<Cart> cart;
    public long sellerid;
    public int type;
}
