package com.starstudio.loser.phrapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.utils.ConvertUriUtils;
import com.starstudio.loser.phrapp.common.utils.ShareUtils;
import com.starstudio.loser.phrapp.common.view.PHRShareDialog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import es.dmoral.toasty.Toasty;

public class MainActivity extends PHRActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final PHRShareDialog dialog = new PHRShareDialog(MainActivity.this);
        dialog.setListener(new PHRShareDialog.OnShareItemClickListener() {
            @Override
            public void onShareItemClickListener(int which, String title, String text) {
                switch (which) {
                    case PHRShareDialog.WEIBO:
                        ShareUtils.shareWeibo("测试title", "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", "测试text", new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                Toasty.success(MainActivity.this, "分享成功", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                Toasty.error(MainActivity.this, "出错啦", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.QQ:
                        ShareUtils.shareQQ("测试title", "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", "测试text", new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                Toasty.success(MainActivity.this, "分享成功", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                Toasty.error(MainActivity.this, "出错啦", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.WECHAT:
                        ShareUtils.shareWechat("测试title", "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", "测试text", new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                Toasty.success(MainActivity.this, "分享成功", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                Toasty.error(MainActivity.this, "出错啦", Toast.LENGTH_SHORT, true).show();
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        break;
                }
            }

            @Override
            public void onCancelItemClickListener() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(MainActivity.this, "取消分享", Toast.LENGTH_SHORT).show();
            }
        });


        final EditText editText = (EditText) findViewById(R.id.edit);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            @Override
            public void onClick(View v) {
                dialog.showShareDialog();
////                Intent intent = new Intent(Intent.ACTION_SEND);
////                intent.putExtra(Intent.EXTRA_TEXT, "try to share");
////                intent.setType("text/plain");
////                startActivity(Intent.createChooser(intent, "share"));
//                OnekeyShare oks = new OnekeyShare();
//
//                oks.setDialogMode(true);
//                //关闭sso授权
//                oks.disableSSOWhenAuthorize();
////                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.phr_logo_fl);
////
////                oks.setImageData(bitmap);
//
//                oks.setImageUrl("http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png");
//
//                // title标题，微信、QQ和QQ空间等平台使用
//                oks.setTitle(getString(R.string.phr_share_string));
//                // text是分享文本，所有平台都需要这个字段
//                oks.setText("我是分享文本");
//
//
//                // comment是我对这条分享的评论，仅在人人网使用
//                oks.setComment("我是测试评论文本");
//                // 启动分享GUI
//                oks.show(getActivity());


            }
        });

    }
}
