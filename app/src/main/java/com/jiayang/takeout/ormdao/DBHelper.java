package com.jiayang.takeout.ormdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jiayang.takeout.common.TUApp;
import com.jiayang.takeout.ormdao.bean.AddressBean;
import com.jiayang.takeout.ormdao.bean.UserBean;

import java.sql.SQLException;

/**
 * Created by 张 奎 on 2017-09-11 16:24.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASENAME = "takeout.db";
    private static final int DATABASEVERSION = 1;
    private static DBHelper instance;


    private DBHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    // 单例模式 使用 线程锁
    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {         //再次判断是否为Null 防止多线程过来时 会多次创建对象
                    instance = new DBHelper(TUApp.getContext());
                    instance.getWritableDatabase();
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserBean.class);
            TableUtils.createTable(connectionSource, AddressBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
