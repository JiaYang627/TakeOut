package com.jiayang.takeout.v.iview;

import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.v.base.IBaseView;

import java.util.List;

/**
 * Created by 张 奎 on 2017-09-26 11:29.
 */

public interface IreceiptActivityView extends IBaseView {
    void fillData(List<AddressBean> addressBeen);
}
