package com.jiayang.takeout.ormdao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static android.R.attr.id;

/**
 * Created by 张 奎 on 2017-09-11 16:25.
 */

@DatabaseTable(tableName = "t_address")
public class AddressBean {

    // 自增
    @DatabaseField(generatedId = true)
    public int _id;
    //foreign = true 是外键 foreignColumnName = "_id"外键存放的是UserBean表中_id 字段
    @DatabaseField(canBeNull = false , foreign = true , foreignColumnName = "_id" , columnName = "user_id")
    public UserBean mUserBean;
    public AddressBean() {
    }



    @DatabaseField(canBeNull = false)
    public String name;
    @DatabaseField(canBeNull = false)
    public String sex;
    @DatabaseField(canBeNull = false)
    public String phone;
    @DatabaseField(canBeNull = false)
    public String receiptAddress;
    @DatabaseField(canBeNull = false)
    public String detailAddress;
    @DatabaseField(canBeNull = false)
    public String label;
    @DatabaseField(canBeNull = false)
    public long timeStamp;
    @DatabaseField(canBeNull = false)
    public double longitude;
    @DatabaseField(canBeNull = false)
    public double latitude;







    public AddressBean(String name, String sex, String phone, String receiptAddress, String detailAddress, String label ,double longitude ,double latitude) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.receiptAddress = receiptAddress;
        this.detailAddress = detailAddress;
        this.label = label;
        this.longitude=longitude;
        this.latitude=latitude;

        timeStamp=System.currentTimeMillis();
    }
//    public int get_id() {
//        return _id;
//    }
//
//    public void set_id(int _id) {
//        this._id = _id;
//    }
//
//    public String getGoodsAddress() {
//        return goodsAddress;
//    }
//
//    public void setGoodsAddress(String goodsAddress) {
//        this.goodsAddress = goodsAddress;
//    }
//
//    public String getVillage() {
//        return village;
//    }
//
//    public void setVillage(String village) {
//        this.village = village;
//    }
//
//    public UserBean getUserBean() {
//        return mUserBean;
//    }
//
//    public void setUserBean(UserBean userBean) {
//        mUserBean = userBean;
//    }
}
