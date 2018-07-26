package com.starstudio.loser.phrapp.common.http;

/*
    create by:loser
    date:2018/7/24 10:38
*/

import android.content.Context;

import retrofit2.Retrofit;


import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client sInstance;
    private Retrofit mRetrofit;

    private Client() { }

    public static synchronized Client getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Client();
            sInstance.mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://v.juhe.cn/toutiao/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sInstance;
    }

    public Retrofit getClient() {
        return mRetrofit;
    }
}
