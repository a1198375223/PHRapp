package com.starstudio.loser.phrapp.item.community.comment.show.presenter;

/*
    create by:loser
    date:2018/8/7 17:06
*/

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface ShowReplyEventListener extends BaseEventListener {
    void saveComment(String text, int id);
}
