package com.jiayang.takeout.utils;

import java.text.NumberFormat;

/**
 * Created by p on 2016/8/25.
 */
public class NumberFormatUtils {

    private NumberFormatUtils(){

    }

    //格式化数据保留两位小数  并且带￥
    public static String formatDigits(double data){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(data);
    }
}
