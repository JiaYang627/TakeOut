package com.jiayang.takeout.v.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayang.takeout.R;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.p.activity.ReceiptActivityPst;
import com.jiayang.takeout.utils.LogUtils;
import com.jiayang.takeout.utils.ToastUtils;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IreceiptActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张 奎 on 2017-09-26 11:28.
 */

public class ReceiptActivity extends BaseActivity<ReceiptActivityPst> implements IreceiptActivityView {
    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.rv_receipt_address)
    RecyclerView mRvReceiptAddress;
    @BindView(R.id.tv_add_address)
    TextView mTvAddAddress;
    private MyAdapter mAdapter;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receipt_address;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        mPresenter.setType(1);
        super.onResume();
    }

    @OnClick({R.id.ib_back, R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                this.finish();
                break;
            case R.id.tv_add_address:
                mPresenter.goToEditAddress(-1);
                break;
        }
    }

    @Override
    public void fillData(Object o) {
        List<AddressBean> addressBeen = (List<AddressBean>) o;
        if (addressBeen != null && addressBeen.size() > 0) {

            if (mAdapter == null) {
                mAdapter = new MyAdapter(addressBeen);
            } else {
                mAdapter.setBeen(addressBeen);
            }
            mRvReceiptAddress.setAdapter(mAdapter);
            mRvReceiptAddress.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL , false));
        } else {
            ToastUtils.initToast("您需要填写一个地址");
            mPresenter.goToEditAddress(1);
        }



    }
    class MyAdapter extends RecyclerView.Adapter {
        private List<AddressBean> been;

        public MyAdapter(List<AddressBean> been) {
            this.been = been;
        }

        public void setBeen(List<AddressBean> been) {
            this.been = been;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_receipt_address, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            AddressBean data = been.get(position);

            viewHolder.setData(data);
        }


        private int getlabelIndex(String label) {
            int index = 0;
            for (int i = 0; i < addressLabels.length; i++) {
                if (label.equals(addressLabels[i])) {
                    index = i;
                    break;
                }
            }
            return index;
        }

        String[] addressLabels = new String[]{"家", "公司", "学校"};
        int[] bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色

        };

        @Override
        public int getItemCount() {
            return been.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_sex)
            TextView tvSex;
            @BindView(R.id.tv_phone)
            TextView tvPhone;
            @BindView(R.id.tv_label)
            TextView tvLabel;
            @BindView(R.id.tv_address)
            TextView tvAddress;
            @BindView(R.id.iv_edit)
            ImageView ivEdit;
            private AddressBean data;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            @OnClick(R.id.iv_edit)
            public void onClick(View view) {

//                Intent intent = new Intent(MyApplication.getContext(), EditReceiptAddressActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("id", data._id);
//                MyApplication.getContext().startActivity(intent);
                mPresenter.goToEditAddress(data._id);
            }


            public void setData(AddressBean data) {
                this.data = data;

                tvName.setText(data.name);
                tvSex.setText(data.sex);

                tvPhone.setText(data.phone);


                if (!TextUtils.isEmpty(data.label)) {
                    tvLabel.setVisibility(View.VISIBLE);
                    int index = getlabelIndex(data.label);
                    tvLabel.setText(addressLabels[index]);
                    tvLabel.setBackgroundColor(bgLabels[index]);
                } else {
                    tvLabel.setVisibility(View.GONE);
                }

                tvAddress.setText(data.receiptAddress + data.detailAddress);
            }
        }
    }

}
