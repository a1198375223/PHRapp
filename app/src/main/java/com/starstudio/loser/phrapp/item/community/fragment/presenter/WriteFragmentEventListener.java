package com.starstudio.loser.phrapp.item.community.fragment.presenter;

/*
    create by:loser
    date:2018/7/31 15:23
*/

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface WriteFragmentEventListener extends BaseEventListener {
    void post(String title, String text);
}
