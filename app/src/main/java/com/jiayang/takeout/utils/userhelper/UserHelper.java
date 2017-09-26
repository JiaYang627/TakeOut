package com.jiayang.takeout.utils.userhelper;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.jiayang.takeout.common.Constants;
import com.jiayang.takeout.ormdao.bean.UserBean;
import com.jiayang.takeout.utils.PreferenceTool;

/**
 * Created by Forward1129 on 2017/3/20.
 */

public class UserHelper {
    public static boolean isLogin() {
        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
        UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        if (userBean != null) {
            return true;
        }
        return false;
    }

//    public static void removeUser() {
//        PreferenceTool.remove(Constants.SP_Info.SP_USER_INFO);
//        PreferenceTool.remove(Constants.SP_Info.SP_AMOUNT_INFO);
//        PreferenceTool.commit();
//        UserStateEvent userStateEvent = new UserStateEvent(UserStateEvent.LoginState.NO_LOGIN, null);
//        EventBus.getDefault().post(userStateEvent);
//    }
//
//    public static UserInfoVo getCurrentUser(Context context){
//        String userInfo = PreferenceTool.getString(Constants.SP_Info.SP_USER_INFO, "");
//        UserInfoVo userInfoVo = JSON.parseObject(userInfo, UserInfoVo.class);
//        return userInfoVo;
//    }
}
