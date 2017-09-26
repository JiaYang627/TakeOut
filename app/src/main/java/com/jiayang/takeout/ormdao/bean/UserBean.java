package com.jiayang.takeout.ormdao.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 张 奎 on 2017-09-11 16:25.
 */
@DatabaseTable(tableName = "t_user")
public class UserBean {

    // columnName 默认可不写 为属性名 id 是否为主键  默认false
    @DatabaseField(columnName = "_id" , id = true)
    public int _id;

    //当通过 userbean 查询到数据后 把查到的数据装到一个集合中.
    @ForeignCollectionField(eager = true)
    private ForeignCollection<AddressBean> addressList;

    public UserBean() {
    }


    @DatabaseField()
    public String name;
    @DatabaseField()
    public float balance;
    @DatabaseField()
    public int discount;
    @DatabaseField()
    public int integral;
    @DatabaseField()
    public String phone;
    @DatabaseField
    public boolean login;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ForeignCollection<AddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(ForeignCollection<AddressBean> addressList) {
        this.addressList = addressList;
    }

}
