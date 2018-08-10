package com.starstudio.loser.phrapp.common.utils;

/*
    create by:loser
    date:2018/7/24 14:16
*/

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class GlideUtils {

    //加载圆形的图片
    public static void loadRoundImage(final Context context, String url, final ImageView imageView) {
        Glide.with(context).asBitmap().load(url).into(new BitmapImageViewTarget(imageView){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                bitmapDrawable.setCircular(true);
                imageView.setImageDrawable(bitmapDrawable);
            }
        });
    }

    //加载正常图片

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).asBitmap().load(url).into(imageView);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    //加载本地资源图片
    public static void loadLocal(Context context, int res, ImageView imageView) {
        Glide.with(context).load(res).into(imageView);
    }


}
