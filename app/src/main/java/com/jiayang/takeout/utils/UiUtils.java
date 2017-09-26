package com.jiayang.takeout.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by itheima.
 */

public class UiUtils {
    public static int STATUE_BAR_HEIGHT=0;// 记录装填栏的高度

    /**
     * 依据Id查询指定控件的父控件
     * @param v 指定控件
     * @param id 父容器标识
     * @return
     */
    public static ViewGroup getContainder(View v, int id) {
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent.getId() == id) {
            return parent;
        }
        return getContainder(parent, id);
    }
}
