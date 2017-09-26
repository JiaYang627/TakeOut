package com.jiayang.takeout.common;

/**
 * 全局常量类
 */

public class Constants {


    public static final String MAIN_HOME = "首页";
    public static final String MAIN_ORDER = "订单";
    public static final String MAIN_USER = "个人";
    public static final String MAIN_MORE = "更多";
    public static final String SELLER_GOODS = "商品";
    public static final String SELLER_SELLER = "商家";
    public static final String SELLER_RECOMMEND = "评论";


    public static int LOGIN_TYPE_SMS = 2; //短信登录

    // 本地存储的用户信息
    public static class SP_Info {
        public static final String SP_USER_INFO = "data1";
        public static final String SP_AMOUNT_INFO = "data2";
        public static final String FIRST = "appFirst";// 欢迎页 是否是第一次
        public static final String SEARCHHISTORY = "searchHistory";//搜索历史
        public static final String AD_IMAGE_INFO = "adImageInfo";//广告信息
        public static final String QQ_GROUPKEY = "qqgroupkey";//QQ群key
        public static final String QQ_GROUP = "qqgroup";//QQ群
        public static final String SP_JPUSH_ID = "jpushId";//极光设备标识ID
        public static final String HOME_AD_IMAGE_NAME = "homeadimagename";//首页广告图片名字
        public static final String SKIN_URL_STRING = "skinUrlString";//皮肤 存 SP 对应的键
        public static final String SKIN_IS_CHANGE = "ischange";//皮肤 是否换肤 存 SP 对应的键
        public static final String SKIN_URL = "skinUrl";//皮肤 URL 存 SP 对应的键
        public static final String VERSION_NAME = "versionName";//当前版本名
        public static final String LINK = "link";//下载link
        public static final String NEED_UPDATE = "needUpdate";//是否需要版本更新
        public static final String REFERNECE_ID = "ID";//下载成功后 返回的ID
        public static final String VERSIONED_NAME = "versionedName";//下载成功后的版本名
        public static final String DOWNLOAD_FILE_URI = "downloadFileUri";//下载成功后的apk路径
        public static final String OPEN_STICK_BTN = "openStickBtn";//是否开启首页悬浮按钮
        public static final String NEED_CHECK_MONITOR = "isSimulatorBanned";//是否开启模拟器识别
        public static final String FORCEUPDATE_DOWNLOAD_STATE="isDownloadState";//是否下载完成状态
    }
}
