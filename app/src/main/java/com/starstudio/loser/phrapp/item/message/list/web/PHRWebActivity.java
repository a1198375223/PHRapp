package com.starstudio.loser.phrapp.item.message.list.web;

/*
    create by:loser
    date:2018/7/25 14:29
*/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;

public class PHRWebActivity extends PHRActivity{
    private WebView mWebView;
    private WebSettings mSettings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_web);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = null;
        if (bundle != null) {
            url = bundle.getString("url");
        }

        mWebView = (WebView) findViewById(R.id.phr_message_web_view);
        mSettings = mWebView.getSettings();

        //加载网页
        mWebView.loadUrl(url);

        //自动加载图片
        mSettings.setLoadsImagesAutomatically(true);

        //允许加载文件
        mSettings.setAllowFileAccess(true);

        //支持js
        mSettings.setJavaScriptEnabled(true);

        //自适应屏幕
        mSettings.setUseWideViewPort(true);//自动调整图片
        mSettings.setLoadWithOverviewMode(true);//自动缩放屏幕

        //支持缩放
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);

        //隐藏缩放空间
        mSettings.setDisplayZoomControls(false);

        //关闭缓存
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //设置编码格式
        mSettings.setDefaultTextEncodingName("utf-8");


        //js
        mWebView.setWebChromeClient(new WebChromeClient());
        //通知
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgressDialog();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissProgressDialog();
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onPause() {
        mSettings.setJavaScriptEnabled(false);
        super.onPause();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        mSettings.setJavaScriptEnabled(true);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView = null;
        }
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
