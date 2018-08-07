package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/8/1 17:44
*/


import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface ChildEventListener extends BaseEventListener{
    void toRefresh();

    void toLoadMore();

    void startArticleActivity(int position);
}
