package com.jiayang.takeout.widget.imageLoader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jiayang.takeout.R;


/**
 * Created by Administrator on 2017/3/17.
 * 通用的imageLoader，通过改变内部的设置，设置同的图片加载器，如glide，picasso等
 */

public class ImageLoader {






    /**
     * 加载头像
     * @param url
     * @param imageView
     */
    public static void loadAvatarFromUrl(String url, ImageView imageView) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .asBitmap()
                    .placeholder(R.mipmap.icon_photo_new)   //占位符
                    .error(R.mipmap.icon_photo_new)         //错误显示
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)    //磁盘缓存方案：固定尺寸结果
                    .override(DensityUtils.dp2px(imageView.getContext(),65)
                            ,DensityUtils.dp2px(imageView.getContext(),65))
                    .into(imageView);
    }

    /**
     * 加载列表图片
     * @param url
     * @param imageView
     */
    public static void loadListBitmapFromUrl(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.mipmap.food_list_item_image_default)   //占位符
                .error(R.mipmap.icon_photo_new)         //错误显示
                .diskCacheStrategy(DiskCacheStrategy.RESULT)    //磁盘缓存方案：固定尺寸结果
                .override(DensityUtils.dp2px(imageView.getContext(),50)
                        ,DensityUtils.dp2px(imageView.getContext(),50))
                .crossFade()                //默认淡入动画
                .fitCenter()               //缩放方式
                .into(imageView);
    }

    public static void loadImageViewFromUrl(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.mipmap.icon_photo_new)
                .error(R.mipmap.icon_photo_new)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .override(DensityUtils.dp2px(imageView.getContext(), 200), DensityUtils.dp2px(imageView.getContext(), 100))
                .crossFade()                //默认淡入动画
                .fitCenter()                //缩放方式
                .into(imageView);
    }
//    public static void loadListBitmapFromUrlForBask(String url, ImageView imageView) {
//        Glide.with(imageView.getContext())
//                .load(TextUtils.isEmpty(url) ? url : url.contains("dgj.daguanjiajinrong.com") ? url : url + "@5")
//                .placeholder(R.mipmap.icon_photo_new)   //占位符
//                .error(R.mipmap.icon_photo_new)         //错误显示
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)    //磁盘缓存方案：固定尺寸结果
//                .override(DensityUtils.dp2px(imageView.getContext(),120)
//                        ,DensityUtils.dp2px(imageView.getContext(),120))
//                .crossFade()                //默认淡入动画
//                .centerCrop()              //缩放方式
//                .into(imageView);
//    }
//
//    /**
//     * 加载详情大图
//     * @param url
//     * @param imageView
//     */
//    public static void loadDetailsFromUrl(String url, ImageView imageView) {
//
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(R.mipmap.icon_photo_new)   //占位符
//                .error(R.mipmap.icon_photo_new)         //错误显示
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)    //磁盘缓存方案：只存原图
//                .crossFade()                //默认淡入动画
//                .fitCenter()             //缩放方式
//                .into(imageView);
//    }
//    public static void loadBinnerFromUrl(String url, ImageView imageView) {
//
//        Glide.with(imageView.getContext())
//                .load(TextUtils.isEmpty(url) ? url : url.contains("dgj.daguanjiajinrong.com") ? url : url + "@5")
//                .placeholder(R.mipmap.icon_binner)   //占位符
//                .error(R.mipmap.icon_binner)         //错误显示
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)    //磁盘缓存方案：只存原图
//                .crossFade()                //默认淡入动画
//                .fitCenter()             //缩放方式
//                .into(imageView);
//    }
//
//    public static void loadGaussianBlur(String url ,ImageView imageView){
//        Glide.with(imageView.getContext())
//                .load(TextUtils.isEmpty(url) ? url : url.contains("dgj.daguanjiajinrong.com") ? url : url )
//                .placeholder(R.mipmap.icon_havelogin)
//                .error(R.mipmap.icon_havelogin)
//                .crossFade()
//                .bitmapTransform(new BlurTransformation(imageView.getContext(),8,1)) // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
//                .into(imageView);
//    }


}
