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
import com.jiayang.takeout.utils.ToastUtils;
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
    private int mType;

    @Inject
    public ReceiptActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;


    }

    @Override
    public void onTakeView() {
        super.onTakeView();
        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        mUserBean = JSON.parseObject(userInfo, UserBean.class);
        // 此处说明一下 收货地址界面 和 编辑界面使用的是一个Pst 所以在Act中 设置一个Type做判断。
        // 为什么这么做 ？ 使用数据库 增删改查 为避免 初始化相同操作 所以才这样做。
        if (mType == 1) {
            if (dao == null) {
                try {
                    dao = mDBHelper.getDao(AddressBean.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

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
    public int create(AddressBean item){
        UserBean userBean=new UserBean();
        userBean._id=mUserBean._id;
        item.mUserBean=userBean;
        try {
             return dao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 用户输入信息添加
     */
    public void create(String name, String sex, String phone, String receiptAddress, String detailAddress, String label){
        // 写入本地数据库
        AddressBean bean=new AddressBean(name,sex,phone,receiptAddress,detailAddress,label);
        int i = create(bean);
        if(i==1){
            // 写入本地数据库操作成功
            mView.fillData(i);// 添加地址界面
        }else{
//            mView.failed("添加操作失败");
            ToastUtils.initToast("添加操作失败");
        }
        // 发送数据到网络
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

    public void goToEditAddress() {
        mTakeOutNavigete.goToEditAddress(context);
    }

    public void setType(int type) {
        mType = type;
    }
}
