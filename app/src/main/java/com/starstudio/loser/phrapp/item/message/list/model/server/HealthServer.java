package com.starstudio.loser.phrapp.item.message.list.model.server;

/*
    create by:loser
    date:2018/7/24 10:49
*/

import android.content.Context;

import com.starstudio.loser.phrapp.common.http.BaseApiServer;
import com.starstudio.loser.phrapp.common.http.Client;
import com.starstudio.loser.phrapp.item.message.list.model.api.Api;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;

import io.reactivex.Observable;


public class HealthServer extends BaseApiServer {
    private static HealthServer sInstance;
    private Api mApi;


    private HealthServer(Api api) {
        this.mApi = api;
    }

    public static synchronized HealthServer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HealthServer(Client.getInstance(context).getClient().create(Api.class));
        }
        return sInstance;
    }

    public Observable<BaseBean> fetchHealthInfo() {
        return mApi.fetchHealth();
    }
}
