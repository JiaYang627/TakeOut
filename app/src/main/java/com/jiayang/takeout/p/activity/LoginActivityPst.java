package com.jiayang.takeout.p.activity;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.rxhelper.ErrorListener;
import com.jiayang.takeout.m.rxhelper.RequestCallback;
import com.jiayang.takeout.m.service.TakeOutService;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.base.BasePresenter;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.utils.RxUtils;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.iview.IloginActivityView;

import java.sql.Savepoint;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by 张 奎 on 2017-09-22 15:57.
 */

public class LoginActivityPst extends BasePresenter<IloginActivityView> {

    private TakeOutService mTakeOutService;

    @Inject
    public LoginActivityPst(ErrorListener errorListener , TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    public void goToLogin(String userPhone) {
        mTakeOutService.login(userPhone, Constants.LOGIN_TYPE_SMS)
                .compose(RxUtils.<RootNode>getSchedulerTransformer())
                .subscribe(new RequestCallback<RootNode>(this) {
                    @Override
                    public void onNext(@NonNull RootNode rootNode) {
                        super.onNext(rootNode);
                        String data = rootNode.data;
                        UserBean userBean = JSON.parseObject(data, UserBean.class);

                        // 存储数据

                        AndroidDatabaseConnection connection = null;
                        Savepoint start = null;
                        try {
                            Dao<UserBean, ?> dao = mDBHelper.getDao(UserBean.class);


                            // 在添加新的已经登陆用户数据时，需要检查本地数据库中的用户信息，确保他们都是未登录的状态

                            // 工作内容：
                            // 查询所有的用户数据，修改登陆状态为false
                            // 添加新用户，设置状态为true

                            // 事务操作的流程：开启事务     一系列数据库操作     提交    回滚
                            // 问题：1.回滚到那里（还原点的设置）；2.一系列数据库操作不能立即生效

                            connection = new AndroidDatabaseConnection(mDBHelper.getReadableDatabase(), true);


                            // 1.开启事务   还原点的设置
                            start = connection.setSavePoint("start");// 设置还原点的时候有开启事务的操作
                            // 2.一系列数据库操作不能立即生效
                            dao.setAutoCommit(connection, false);// 关闭立即提交（需要进行一些列的操作）
                            // 3.查询所有的用户数据，修改登陆状态为false
                            List<UserBean> userBeen = dao.queryForAll();
                            if (userBeen != null && userBeen.size() > 0) {
                                for (UserBean item :
                                        userBeen) {
                                    item.login = false;
                                    dao.update(item);
                                }
                            }
                            // 4.添加新用户，设置状态为true
                            userBean.login = true;// 已经登陆
                            dao.create(userBean);

                            connection.commit(start);

//                            TUApp.phone = userBean.phone;
//                            TUApp.USERID = userBean._id;
                            PreferenceTool.putString(Constants.SP_Info.SP_USER_INFO , data);
                            PreferenceTool.commit();


                            mView.success();

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (connection != null) {
                                try {
                                    connection.rollback(start);
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                            ToastUtils.initToast("修改本地数据异常");
                        }
                    }
                });
    }
}
