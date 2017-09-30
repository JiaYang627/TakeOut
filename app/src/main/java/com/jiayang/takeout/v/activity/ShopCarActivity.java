package com.jiayang.takeout.v.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsInfoVo;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.activity.ShopCarActivityPst;
import com.jiayang.takeout.utils.NumberFormatUtils;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IshopCarActivityView;
import com.jiayang.takeout.widget.RecycleViewDivider;
import com.jiayang.takeout.widget.ShoppingCartManager;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

/**
 * 购物车Act
 */

public class ShopCarActivity extends BaseActivity<ShopCarActivityPst> implements IshopCarActivityView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.bottom)
    RelativeLayout mBottom;
    @BindView(R.id.cart_rv)
    RecyclerView mCartRv;
    private MyCartAdapter adapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopcar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        initShow();
    }

    private void initToolBar() {
        mToolbar.setTitle(getString(R.string.order_shop_car));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initShow() {
        adapter = new MyCartAdapter();
        mCartRv.setAdapter(adapter);
        mCartRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // 设置分割线
        mCartRv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, 0XE3E0DC));


        mTvMoney.setText(NumberFormatUtils.formatDigits(ShoppingCartManager.getInstance().getMoney() / 100.0));
    }

    class MyCartAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(View.inflate(TUApp.getContext(), R.layout.item_shopcar, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setData(ShoppingCartManager.getInstance().goodsInfos.get(position));
        }

        @Override
        public int getItemCount() {
            return ShoppingCartManager.getInstance().goodsInfos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_iv)
            ImageView mItemIv;
            @BindView(R.id.item_tv_name)
            TextView mItemTvName;
            @BindView(R.id.item_tv_price)
            TextView mItemTvPrice;
            @BindView(R.id.item_tv_num)
            TextView mItemTvNum;

            private GoodsInfoVo data;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, this.itemView);
            }

            public void setData(GoodsInfoVo data) {
                this.data = data;
                Picasso.with(TUApp.getContext()).load(data.icon.replace("Takeout" , "takeout")).into(mItemIv);
                mItemTvName.setText(data.name);
                mItemTvPrice.setText(NumberFormatUtils.formatDigits(data.newPrice));
                mItemTvNum.setText(data.count + "");
            }
        }


    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mPresenter.goToSettle();
    }
}
