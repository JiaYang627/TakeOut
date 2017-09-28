package com.jiayang.takeout.p.activity;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.bean.Cart;
import com.jiayang.takeout.m.bean.OrderOverview;
import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsInfoVo;
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
import com.jiayang.takeout.v.iview.IsettleActivityView;
import com.jiayang.takeout.widget.ShoppingCartManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

import static android.R.attr.multiArch;
import static android.R.attr.type;
import static com.jiayang.takeout.p.activity.ReceiptActivityPst.dao;

/**
 * Created by 张 奎 on 2017-09-24 09:24.
 */

public class SettleActivityPst extends BasePresenter<IsettleActivityView> {

    public static String EXTRA_ADDRESS_ID = "addressId";
    private TakeOutService mTakeOutService;
    private int addressId = -1;

    @Inject
    public SettleActivityPst(ErrorListener errorListener, TakeOutService takeOutService) {
        super(errorListener);
        mTakeOutService = takeOutService;
    }

    public void goToAddress() {
        mTakeOutNavigate.goToAddress(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 200) {
                if (dao == null) {
                    try {
                        dao = mDBHelper.getDao(AddressBean.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                int id = data.getIntExtra(SettleActivityPst.EXTRA_ADDRESS_ID, -1);
                addressId = id;
                findById(id);


            }
        }
    }

    /**
     * 依据用户标识获取对应的地址信息
     *
     * @param userId
     */
    public void findAllByUserId(int userId) {
        try {
            if (dao == null) {
                try {
                    dao = mDBHelper.getDao(AddressBean.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            List<AddressBean> addressBeen = dao.queryForEq("user_id", userId);
            mView.fillData(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void findById(int id) {
        try {
            AddressBean addressBeen = dao.queryForId(id);
            mView.fillData(addressBeen);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goToCreatOrder() {
        if (addressId != -1) {
            String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
            UserBean mUserBean = JSON.parseObject(userInfo, UserBean.class);

            OrderOverview overview = new OrderOverview();
            overview.addressId = addressId;
            overview.sellerid = ShoppingCartManager.getInstance().sellerId;
            overview.type = type;
            overview.userId = mUserBean._id;

            overview.cart = new ArrayList<>();
            for (GoodsInfoVo info : ShoppingCartManager.getInstance().goodsInfos) {
                Cart cart = new Cart();
                cart.id = info.id;
                cart.count = info.count;

                overview.cart.add(cart);
            }
            Object addressJson = JSON.toJSON(overview);
            mTakeOutService.creatOrder(addressJson)
                    .compose(RxUtils.<RootNode>getSchedulerTransformer())
                    .subscribe(new RequestCallback<RootNode>(this) {
                        @Override
                        public void onNext(@NonNull RootNode rootNode) {
                            super.onNext(rootNode);

                            String orderId = rootNode.data;
                            goToOnlinePay(orderId);
                        }
                    });
        } else {
            ToastUtils.initToast("请选择配送地址");
        }
    }

    private void goToOnlinePay(String orderId) {
        mTakeOutNavigate.goToOnlinePay(context, orderId);
    }


}
