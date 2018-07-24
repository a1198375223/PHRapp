package com.starstudio.loser.phrapp.item.message.presenter;

/*
    create by:loser
    date:2018/7/23 22:54
*/

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.message.contract.MessageContract;

public class MessagePresenterImpl extends PHRPresenter<MessageContract.MessageView, MessageContract.MessageModel> implements MessageContract.MessagePresenter {

    public MessagePresenterImpl(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {

    }

    @Override
    protected void onDetach() {

    }

    @Override
    public Fragment getFragment(int position) {
        return null;
    }
}
