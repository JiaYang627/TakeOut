package com.jiayang.takeout.p.activity;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.v.iview.IreceiptActivityView;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by 张 奎 on 2017-09-26 11:28.
 */

public class ReceiptActivityPst extends BasePresenter<IreceiptActivityView> {

    private TakeOutService mTakeOutService;

    // 地址的增、删、改、查
    static Dao<AddressBean, Integer> dao;
    private UserBean mUserBean;

    @Inject
    public ReceiptActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;


    }

    @Override
    public void onTakeView() {
        super.onTakeView();
        if (dao == null) {
            try {
                dao = mDBHelper.getDao(AddressBean.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        mUserBean = JSON.parseObject(userInfo, UserBean.class);
        mTakeOutService.getAddress(mUserBean._id)
                .compose(RxUtils.<RootNode>getSchedulerTransformer())
                .subscribe(new RequestCallback<RootNode>(this) {
                    @Override
                    public void onNext(@NonNull RootNode rootNode) {
                        super.onNext(rootNode);
                        String data = rootNode.data;
                        parserData(data);
                    }
                });
    }

    private void parserData(String data) {
        if (TextUtils.isEmpty(data)) {
            // 读取本地的地址
            findAllByUserId(mUserBean._id);
        } else {
            // 获取网络信息记录入库
            List<AddressBean> addressBeen = JSON.parseArray(data, AddressBean.class);

            for(AddressBean item:addressBeen){
                create(item);
            }
            findAllByUserId(mUserBean._id);
        }
    }

    /**
     * 添加一条地址记录(将服务器的数据插入到本地数据库中)
     * @param item
     */
    public void create(AddressBean item){
        UserBean userBean=new UserBean();
        userBean._id=mUserBean._id;
        item.mUserBean=userBean;
        try {
             dao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依据用户标识获取对应的地址信息
     *
     * @param userId
     */
    public void findAllByUserId(int userId) {
        try {
            List<AddressBean> addressBeen = dao.queryForEq("user_id", userId);
            mView.fillData(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
