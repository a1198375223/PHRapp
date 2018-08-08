package com.starstudio.loser.phrapp.common.utils;

/*
    create by:loser
    date:2018/8/8 10:39
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareUtils {
    //QQ好友分享
    public static void shareQQ(String title, String titleUrl, String text, PlatformActionListener listener){
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        QQ.ShareParams sp = new QQ.ShareParams();
        qq.showUser(null);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl); // 标题的超链接
        sp.setText(text);
        sp.setImageUrl("http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png");
        qq.setPlatformActionListener(listener);
        qq.share(sp);
    }
    //新浪微博
    public static void shareWeibo(String title,String titleUrl,String text, PlatformActionListener listener){
        Platform sina =ShareSDK.getPlatform(SinaWeibo.NAME);
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl); // 标题的超链接
        sp.setText(text);
        sp.setImageUrl("http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png");
        sina.setPlatformActionListener(listener);
        sina.share(sp);
    }
    //微信
    public static void shareWechat(String title,String titleUrl,String text, PlatformActionListener listener){
        Platform weixin =ShareSDK.getPlatform(Wechat.NAME);
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(title);
        sp.setTitleUrl(titleUrl); // 标题的超链接
        sp.setImageUrl("http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png");
        sp.setUrl(titleUrl);
        sp.setText(text);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        weixin.setPlatformActionListener(listener);
        weixin.share(sp);
    }
}
