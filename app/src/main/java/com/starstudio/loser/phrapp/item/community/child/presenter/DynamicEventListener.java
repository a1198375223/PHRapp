package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/8/7 11:40
*/

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface DynamicEventListener extends BaseEventListener {
    void toRefresh();

    void toLoadMore();

    void startArticleActivity(int position);

    void toTransfer(int position);

    void toShare(int position);

    void toComplaints(int position);

    void toCollect(int position);
}
