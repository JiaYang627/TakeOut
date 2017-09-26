package com.jiayang.takeout.widget.imageLoader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by 张 奎 on 2017-09-06 15:58.
 */

public class ConfigGlide implements GlideModule {

    public static final String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+ "weiyi"+File.separator;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //默认内存缓存
        MemorySizeCalculator memorySizeCalculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = memorySizeCalculator.getMemoryCacheSize();
        int defalutBitmapPoolSize = memorySizeCalculator.getBitmapPoolSize();
        //自定义内存缓存，这里没有用到，暂时使用Glide默认的算法
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一

        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize));
        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(defalutBitmapPoolSize));

        int diskCacheSize = 1024 * 1024 * 300;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(imagePath, "glide", diskCacheSize));
        Log.i("-----a----------",imagePath);

        //设置图片解码格式
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


}
