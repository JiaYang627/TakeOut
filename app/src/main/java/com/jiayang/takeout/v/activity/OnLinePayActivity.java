package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.bean.OrderPayInfoVo;
import com.jiayang.takeout.m.bean.PaymenVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.OnLinePayActivityPst;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IonLinePayActivityView;
import com.jiayang.takeout.widget.imageLoader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 支付页面
 */

public class OnLinePayActivity extends BaseActivity<OnLinePayActivityPst> implements IonLinePayActivityView {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_residualTime)
    TextView mTvResidualTime;
    @BindView(R.id.tv_order_name)
    TextView mTvOrderName;
    @BindView(R.id.ll_order_detail)
    LinearLayout mLlOrderDetail;
    @BindView(R.id.tv_pay_money)
    TextView mTvPayMoney;
    @BindView(R.id.ll_pay_type_container)
    LinearLayout mLlPayTypeContainer;
    @BindView(R.id.bt_confirm_pay)
    Button mBtConfirmPay;


    private Handler mHandler = new Handler();

    private  int paymentType=-1;
    ArrayList<CheckBox> cbs=new ArrayList<>();
    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_pay;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void fillData(OrderPayInfoVo orderPayInfoVo, String orderId) {
        mTvOrderName.setText("第"+orderId+"号订单");
        mHandler.post(new MyResidualTimerTask(orderPayInfoVo.payDownTime * 60));
        mTvPayMoney.setText("￥" + orderPayInfoVo.money);

        addPayment(orderPayInfoVo.paymentInfo);
    }
    private void addPayment(List<PaymenVo> payments) {
        for ( PaymenVo item : payments) {
            // 分割线
            View view = new View(this);
            view.setBackgroundColor(0xfd9b9999);
            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()) + .5);
            mLlPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, h);

            view = View.inflate(this, R.layout.item_online_pay, null);
//            Picasso.with(this).load(item.url.replace("Takeout", "takeout")).into((ImageView) view.findViewById(R.id.iv_pay_logo));
            ImageLoader.loadAvatarFromUrl(item.url.replace("Takeout", "takeout"),(ImageView) view.findViewById(R.id.iv_pay_logo));
            ((TextView) view.findViewById(R.id.tv_pay_name)).setText(item.name);
            CheckBox cb = (CheckBox) view.findViewById(R.id.cb_pay_selected);

            // 如何做到点击某个CheckBox知道起对应的支付信息
            cb.setTag(item.id);
            cbs.add(cb);

            mLlPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        setCheckBoxCheckedListener();

    }


    private void setCheckBoxCheckedListener() {
        for(CheckBox cb :cbs){
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 如果集合中的与传递进来的buttonView相等，说明该cb为用户选择的，其他的需要调整选择状态

                    if(isChecked){
                        for(CheckBox item:cbs){
                            if(item!=buttonView){
                                item.setChecked(false);
                            }else{
                                paymentType= (int) item.getTag();
                            }
                        }
                    }
                }
            });
        }
    }

    private class MyResidualTimerTask implements Runnable {

        private int time;

        public MyResidualTimerTask(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            time = time - 1;
            if (time == 0) {
                mBtConfirmPay.setEnabled(false);
            }
            int m = time / 60;
            int s = time % 60;
            mTvResidualTime.setText("支付剩余时间:" + m + "分" + s + "秒");
            mHandler.postDelayed(this, 999);
        }
    }
}
