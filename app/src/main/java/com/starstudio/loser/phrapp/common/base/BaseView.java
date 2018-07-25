package com.starstudio.loser.phrapp.common.base;

/*
    create by:loser
    date:2018/7/23 13:06
*/


public interface BaseView<E extends BaseEventListener> {
    void setEventListener(E eventListener);
}
