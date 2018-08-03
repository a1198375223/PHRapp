package com.starstudio.loser.phrapp.item.message.presenter;

/*
    create by:loser
    date:2018/7/25 14:16
*/

import android.support.v4.app.Fragment;

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface MessageEventListener extends BaseEventListener {
    Fragment getFragment(int position);
}
