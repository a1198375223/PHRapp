package com.starstudio.loser.phrapp.item.community.search.presenter;

/*
    create by:loser
    date:2018/8/9 13:31
*/

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface SearchEventListener extends BaseEventListener {
    void toFindAndSave(String history);

    void toFind(String search);

    void clearHistory();

    void toFindHistory();

    void startArticleActivity(int position);
}
