package com.starstudio.loser.phrapp.item.message.list.model.api;

/*
    create by:loser
    date:2018/7/24 10:18
*/

import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;


import io.reactivex.Observable;
import retrofit2.http.POST;


public interface Api {
    @POST("type=jiankang&key=b0cd78efd4f4516ba45b4a747ae6d956")
    Observable<BaseBean> fetchHealth();

}
