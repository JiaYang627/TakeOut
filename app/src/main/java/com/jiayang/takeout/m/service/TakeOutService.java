package com.jiayang.takeout.m.service;

import com.jiayang.takeout.m.bean.RootNode;
import com.jiayang.takeout.m.bean.homeVo.HomeInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 张 奎 on 2017-09-11 15:12.
 */

public interface TakeOutService {

    //    http://apis.juhe.cn/  //baseurl
    String BASE_URL = "http://192.168.35.118:8080/";
    String KEY = "daf8fa858c330b22e342c882bcbac622";
    // mobile/get  //相对url
// ?phone=13812345678&key=daf8fa858c330b22e342c882bcbac622  参数  Query查询参数  拼接参数
//    @GET("mobile/get")
//    Observable<LocationBean> getLocation(@Query("phone") String phoneNumber, @Query("key") String key);


//    @FormUrlEncoded
//    @POST("appi/version_update.php")
//    Observable<VersionVo> getVersionInfo(@Field("") int temp);

    /**
     * 获取首页数据
     *
     * @return
     */
    @GET("takeoutService/home")
    Observable<RootNode> home();

    /**
     * 获取店铺商品信息
     *
     * @param sellerId
     * @return
     */
    @GET("takeoutService/goods")
    Observable<RootNode> getGoodsInfo(@Query("sellerId") long sellerId);


    /**
     * 登录
     *
     * @param phone
     * @param typeSms
     * @return
     */
    @GET("takeoutService/login")
    Observable<RootNode> login(@Query("phone") String phone, @Query("type") int typeSms);

    /**
     * 获取用户地址
     *
     * @param sellerId
     * @return
     */
    @GET("takeoutService/address")
    Observable<RootNode> getAddress(@Query("userId") long sellerId);


    /**
     * 提交订单 获取订单编号
     * @param addressJson
     * @return
     */
    @FormUrlEncoded
    @POST("takeoutService/order")
    Observable<RootNode> creatOrder(@Field("orderOverview") Object addressJson);


    /**
     * 请求支付信息
     * @param orderId
     * @return
     */
    @GET("takeoutService/pay")
    Observable<RootNode> getOrderPayInfo(@Query("orderId") String orderId);
}
