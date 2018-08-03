package com.starstudio.loser.phrapp.item.message.list.model.api;

/*
    create by:loser
    date:2018/7/24 10:18
*/

import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {
    @FormUrlEncoded
    @POST("index")
    Observable<BaseBean> fetchData(@Field("type") String type,
                                   @Field("key") String key);

}
