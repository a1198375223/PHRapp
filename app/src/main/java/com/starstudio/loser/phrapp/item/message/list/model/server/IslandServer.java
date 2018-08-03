package com.starstudio.loser.phrapp.item.message.list.model.server;

/*
    create by:loser
    date:2018/7/25 13:59
*/

import android.content.Context;

import com.starstudio.loser.phrapp.common.http.BaseApiServer;
import com.starstudio.loser.phrapp.common.http.Client;
import com.starstudio.loser.phrapp.item.message.list.model.api.Api;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;

import io.reactivex.Observable;

public class IslandServer extends BaseApiServer {
    private static IslandServer sInstance;
    private Api mApi;
    private final String key = "b0cd78efd4f4516ba45b4a747ae6d956";


    private IslandServer(Api api) {
        this.mApi = api;
    }

    public static synchronized IslandServer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new IslandServer(Client.getInstance(context).getClient().create(Api.class));
        }
        return sInstance;
    }

    public Observable<BaseBean> fetchInfo() {
        return mApi.fetchData("guonei", key);
    }
}
