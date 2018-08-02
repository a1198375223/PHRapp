package com.starstudio.loser.phrapp.item.community.presenter;

/*
    create by:loser
    date:2018/7/30 15:46
*/

import android.support.v4.app.Fragment;
import android.view.View;

import com.starstudio.loser.phrapp.common.base.BaseEventListener;

public interface CommunityEventListener extends BaseEventListener {
    Fragment getFragment(int position);

    void startWriteFragment();
}
