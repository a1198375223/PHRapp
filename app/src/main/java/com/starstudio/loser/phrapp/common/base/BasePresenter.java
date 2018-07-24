package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:06
*/

public interface BasePresenter<V extends BaseView, M extends BaseModel> {
    void setView(V view);

    void setModel(M model);

    void attach();

    void detach();


}
