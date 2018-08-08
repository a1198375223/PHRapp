package com.starstudio.loser.phrapp.item.community.comment.presenter;

/*
    create by:loser
    date:2018/8/3 17:44
*/


import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface ArticleEventListener extends BaseEventListener {
    void saveComment(String text, int id);

    void loadComment();
}
