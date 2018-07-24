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
    private MessageContract.MessageView mMessageView;

    public MessagePresenterImpl(Activity activity) {
        super(activity);

    }

    @Override
    protected void onAttach() {
         mMessageView = getView();
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public Fragment getFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 1:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 2:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 3:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 4:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 5:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 6:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 7:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 8:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 9:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 10:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            case 11:
                fragment = mMessageView == null ? null : new Fragment();
                break;
            default:
        }
        return fragment;
    }
}
