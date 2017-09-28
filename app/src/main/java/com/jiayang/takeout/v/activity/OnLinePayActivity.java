package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jiayang.takeout.R;
import com.jiayang.takeout.m.bean.OrderPayInfoVo;
import com.jiayang.takeout.m.bean.PaymenVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.p.activity.OnLinePayActivityPst;
import com.jiayang.takeout.utils.alipay.PayResult;
import com.jiayang.takeout.utils.alipay.SignUtils;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IonLinePayActivityView;
import com.jiayang.takeout.widget.ShoppingCartManager;
import com.jiayang.takeout.widget.imageLoader.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

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


    private int paymentType = -1;
    ArrayList<CheckBox> cbs = new ArrayList<>();
    private static final int SDK_PAY_FLAG = 1;
    private boolean isFirst = true;

    //商户PID
    public static final String PARTNER = "2088011085074233";
    //商户收款账号
    public static final String SELLER = "917356107@qq.com";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL51jaxQhxW9PnWpW+nz6yJ76tp9eGFXmfGnuxMK+Pmx/qavdsewXOLBfI2OSCR39TzxwMYvCmUrnrt0fVSa7mblbNos2FnMM9ijnx8bsAAhm+i7BKhuaHMunJKH69L+D753zH3P1YIh0ly5DnAr3WPqHydp384qBvb8NS9Tay0HAgMBAAECgYB82PIVknP6fCMFXg8yPQJViIVa1ASlSpdPIXQv93FdvKABA+QI4kMBIXRUFoCT506KtK55OzzFNOLIXoQJgcXj69z0l6pmjJJgXMaBW/9rOzelot13CiGatrIrGngEZO+bCBTud/jQA598zjZ1g182tT+FLDL7GIftW2hC8GqtAQJBAN+XrYsyfL+uSmLdAVEz1vzziU1naGr10Msm1jMnnO/JYdB+84j7FSHxsQ4YOgsmeN5YVsJcVfc/CReOxknns38CQQDaEHnVPDt+Z7sqT7bN0UKh0/CrqkDTiIjhz1lJyIIoqVRoeJjJn1wlEKBV5R9gkTJutQTVU19XFtblMEnOy6p5AkEAw170rEmMSa0QoHw+d2bVtydR1QnDapqqO6kOx5oYfkm4J4eWYx4J5CQdMpSmuzF9scL85E3sa+NvnV8LEm7cHwJALtXzFPWG4bNt47yTSslzQka/Hl/G5Kginj1mtA44xnr4AihEyKlNpThY95nqj1cgOd7vVtI9W/sv1LH2aFAeIQJBAIqXbMc6xGVfuiFAJKtg+AFNMBP0UOEgMEoKo4RPFp21nBhFgL9/WYM4ZjyHUdr45rCySAqQovw4DCHLfQZC23I=";
    //支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+dY2sUIcVvT51qVvp8+sie+rafXhhV5nxp7sTCvj5sf6mr3bHsFziwXyNjkgkd/U88cDGLwplK567dH1Umu5m5WzaLNhZzDPYo58fG7AAIZvouwSobmhzLpySh+vS/g++d8x9z9WCIdJcuQ5wK91j6h8nad/OKgb2/DUvU2stBwIDAQAB";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OnLinePayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OnLinePayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OnLinePayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

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
        mTvOrderName.setText("第" + orderId + "号订单");
        mTvPayMoney.setText("￥" + orderPayInfoVo.money);

        if (isFirst) {
            addPayment(orderPayInfoVo.paymentInfo);
            mHandler.post(new MyResidualTimerTask(orderPayInfoVo.payDownTime * 60));
            isFirst = false;
        }
    }

    private void addPayment(List<PaymenVo> payments) {
        for (PaymenVo item : payments) {
            // 分割线
            View view = new View(this);
            view.setBackgroundColor(0xfd9b9999);
            int h = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()) + .5);
            mLlPayTypeContainer.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, h);

            view = View.inflate(this, R.layout.item_online_pay, null);
//            Picasso.with(this).load(item.url.replace("Takeout", "takeout")).into((ImageView) view.findViewById(R.id.iv_pay_logo));
            ImageLoader.loadAvatarFromUrl(item.url.replace("Takeout", "takeout"), (ImageView) view.findViewById(R.id.iv_pay_logo));
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
        for (CheckBox cb : cbs) {
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 如果集合中的与传递进来的buttonView相等，说明该cb为用户选择的，其他的需要调整选择状态

                    if (isChecked) {
                        for (CheckBox item : cbs) {
                            if (item != buttonView) {
                                item.setChecked(false);
                            } else {
                                paymentType = (int) item.getTag();
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

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay() {
        // 订单
        String orderInfo = getOrderInfo(ShoppingCartManager.getInstance().name, "通过外卖项目订购的", "0.01");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(OnLinePayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    @OnClick(R.id.bt_confirm_pay)
    public void onViewClicked() {
        pay();
    }
}
