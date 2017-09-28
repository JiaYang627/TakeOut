package com.jiayang.takeout.v.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.R;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.m.component.ApiComponent;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.p.activity.ReceiptActivityPst;
import com.jiayang.takeout.utils.PreferenceTool;
import com.jiayang.takeout.v.base.BaseActivity;
import com.jiayang.takeout.v.iview.IreceiptActivityView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.id;


/**
 * Created by 张 奎 on 2017-09-26 15:19.
 */

public class EditAddressActivity extends BaseActivity<ReceiptActivityPst> implements IreceiptActivityView {

    @BindView(R.id.ib_back)
    ImageButton mIbBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ib_delete_address)
    ImageButton mIbDeleteAddress;
    @BindView(R.id.rl_left)
    TextView mRlLeft;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_women)
    RadioButton mRbWomen;
    @BindView(R.id.rg_sex)
    RadioGroup mRgSex;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.ib_delete_phone)
    ImageButton mIbDeletePhone;
    @BindView(R.id.tv_receipt_address)
    TextView mTvReceiptAddress;
    @BindView(R.id.et_detail_address)
    EditText mEtDetailAddress;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.ib_select_label)
    ImageView mIbSelectLabel;
    @BindView(R.id.bt_ok)
    Button mBtOk;
    private UserBean mUserBean;
    private int id;
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void inject(ApiComponent apiComponent) {
        apiComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_receipt_address;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        mPresenter.setType(2);
        super.onResume();

        if (Constants.Location.isChange) {
            mTvReceiptAddress.setText(Constants.Location.TITLE + "\r\n" + Constants.Location.SNIPPET);

            // 纬度
            mLatitude = Constants.Location.LATITUDE;
            // 经度
            mLongitude = Constants.Location.LONGITUDE;


            Constants.Location.isChange = false;
            Constants.Location.TITLE = "";
            Constants.Location.SNIPPET = "";
            Constants.Location.LONGITUDE = 0;
            Constants.Location.LATITUDE = 0;
        }

    }



    private void init() {
        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        mUserBean = JSON.parseObject(userInfo, UserBean.class);
        if (!TextUtils.isEmpty(mUserBean.phone)) {
            mEtPhone.setText(mUserBean.phone);
            mIbDeletePhone.setVisibility(View.VISIBLE);
        }

        // 是否有地址的编号传递到编辑界面
        // 如果有，地址的修改或删除
        // 如果没有，添加地址
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            mTvTitle.setText("修改地址");
            mIbDeleteAddress.setVisibility(View.VISIBLE);

//             需要将本地地址信息查询出来，将值设置到界面上
            mPresenter.findById(id);

        } else {
            mTvTitle.setText("新增地址");
            mIbDeleteAddress.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick({R.id.ib_back, R.id.ib_delete_address, R.id.ib_delete_phone, R.id.bt_ok, R.id.ib_select_label ,R.id.tv_receipt_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_receipt_address:
//                Intent intent = new Intent(this, SelectLocationActivity.class);
//                startActivityForResult(intent, 200);
                mPresenter.goToLocation();
                break;

            case R.id.ib_back:
                break;
            case R.id.ib_delete_address:
                showDeleteAlert();
                break;
            case R.id.ib_delete_phone:
                break;
            case R.id.ib_select_label:
                // 选择地址标签
                showLabelAlert();
                break;
            case R.id.bt_ok:
                if (checkReceiptAddressInfo()) {

                    //获取界面数据
                    String name = mEtName.getText().toString().trim();
                    String sex = "";
                    int checkedRadioButtonId = mRgSex.getCheckedRadioButtonId();
                    switch (checkedRadioButtonId) {
                        case R.id.rb_man:
                            sex = "先生";
                            break;
                        case R.id.rb_women:
                            sex = "女士";
                            break;
                    }
                    String phone = mEtPhone.getText().toString().trim();
                    String receiptAddress = mTvReceiptAddress.getText().toString().trim();
                    String detailAddress = mEtDetailAddress.getText().toString().trim();
                    String label = mTvLabel.getText().toString();

                    if (id != -1) {
                        mPresenter.update(id, name, sex, phone, receiptAddress, detailAddress, label , mLongitude , mLatitude);
                    } else {
                        mPresenter.create(name, sex, phone, receiptAddress, detailAddress, label, mLongitude , mLatitude);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        title
//                snippet
//        intent.putExtra("latitude",point.getLatitude());
//        intent.putExtra("longitude", point.getLongitude());


//        if (resultCode == 200) {
//            String title = data.getStringExtra("title");
//            String snippet = data.getStringExtra("snippet");
//
//            tvReceiptAddress.setText(title + "\r\n" + snippet);
//
//            latitude = data.getDoubleExtra("latitude", 0);
//            longitude = data.getDoubleExtra("longitude", 0);
//        }

    }

    /**
     * 提示用户是否删除
     */
    private void showDeleteAlert() {
        new AlertDialog.Builder(this)
                .setTitle("删除地址")
                .setMessage("确定要删除地址吗？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delete(id);
                    }
                })
                .create().show();
    }

    private void showLabelAlert() {
        new AlertDialog.Builder(this)
                .setTitle("选择标签")
                .setItems(addressLabels, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which != 0) {
                            mTvLabel.setText(addressLabels[which]);
                            mTvLabel.setBackgroundColor(bgLabels[which]);
                        }
                    }
                })
                .create().show();
    }

    String[] addressLabels = new String[]{"无", "家", "公司", "学校"};
    int[] bgLabels = new int[]{
            0,
            Color.parseColor("#fc7251"),//家  橙色
            Color.parseColor("#468ade"),//公司 蓝色
            Color.parseColor("#02c14b"),//学校   绿色

    };


    //校验数据
    public boolean checkReceiptAddressInfo() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        String receiptAddress = mTvReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请填写收获地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        String address = mEtDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isMobileNO(String phone) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return phone.matches(telRegex);
    }


    private int getIndexLabel(String label) {
        int index = 0;
        for (int i = 0; i < addressLabels.length; i++) {
            if (label.equals(addressLabels[i])) {
                index = i;
                break;
            }
        }
        return index;
    }
    @Override
    public void fillData(Object o) {
        // 更新界面
        if (o instanceof AddressBean) {
            AddressBean bean = (AddressBean) o;
            mEtName.setText(bean.name);
            mEtName.setSelection(bean.name.length());//让光标在最后

            if (!TextUtils.isEmpty(bean.sex)) {
                if (bean.sex.equals("先生")) {
                    mRbMan.setChecked(true);
                } else {
                    mRbWomen.setChecked(true);
                }
            }

            mEtPhone.setText(bean.phone);
            mTvReceiptAddress.setText(bean.receiptAddress);
            mEtDetailAddress.setText(bean.detailAddress);

            if (!TextUtils.isEmpty(bean.label)) {
                int index = getIndexLabel(bean.label);
                mTvLabel.setText(addressLabels[index]);
                mTvLabel.setBackgroundColor(bgLabels[index]);
            }
        } else {
            finish();
        }
    }
}
