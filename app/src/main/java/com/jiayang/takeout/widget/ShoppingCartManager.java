package com.jiayang.takeout.widget;

import com.jiayang.takeout.m.bean.goodsFrgVo.GoodsInfoVo;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by itheima.
 */

public class ShoppingCartManager {
    private static ShoppingCartManager instance;

    private ShoppingCartManager() {
    }

    public static ShoppingCartManager getInstance() {
        if (instance == null) {
            instance = new ShoppingCartManager();
        }
        return instance;
    }


    /**
     * a)	商品列表（所在分类的标识信息）
     * 增加和减少的操作均会涉及到频繁查询、添加、删除
     * CopyOnWriteArrayList集合进行处理
     * b)	商家信息：标识、名称、logo链接
     * c)	记录并计算商品总价和商品数量
     */

    public CopyOnWriteArrayList<GoodsInfoVo> goodsInfos = new CopyOnWriteArrayList<>();
    public long sellerId;
    public String name;
    public String url;
    public int sendPrice;

    private Integer totalNum = 0;
    private Integer money = 0;// 保存到分（分）


    /**
     * 添加
     *
     * @param info
     */
    public Integer addGoods(GoodsInfoVo info) {
        int num = 0;
        // 判断容器中是否含有该商品
        // 如果有做++
        // 如果没有，添加一条记录

        boolean isContain = false;
        for (GoodsInfoVo item :
                goodsInfos
                ) {
            if (item.id == info.id) {
                item.count++;
                num = item.count;
                isContain = true;
                break;
            }
        }

        if (!isContain) {
            num = info.count = 1;
            goodsInfos.add(info);
        }


        return num;
    }

    /**
     * 减少操作
     *
     * @param info
     */
    public Integer minusGoods(GoodsInfoVo info) {
        Integer num = 0;
        // 做- -
        // 判断：减少后数量是为0
        // 为0 做删除商品处理

        for (GoodsInfoVo item : goodsInfos) {
            if (item.id == info.id) {
                item.count--;
                if (item.count == 0) {
                    goodsInfos.remove(item);
                }
                num = item.count;
                break;
            }
        }
        return num;
    }

    /**
     * 获取商品总数
     * @return
     */
    public Integer getTotalNum() {
        totalNum=0;
        for (GoodsInfoVo item :
                goodsInfos) {
            totalNum += item.count;
        }
        return totalNum;
    }

    public Integer getMoney(){
        money=0;
        for (GoodsInfoVo item :
                goodsInfos) {
            money += (int)(item.newPrice*100);
        }
        return money;
    }

    /**
     * 情况所有选中的商品
     */
    public void clear() {
        goodsInfos.clear();
    }

    /**
     * 依据标识获取商品数量信息
     * @param id
     */
    public Integer getGoodsIdNum(int id) {
        for (GoodsInfoVo item :
                goodsInfos) {
            if (id == item.id){
                return item.count;
            }
        }
        return 0;
    }
}
