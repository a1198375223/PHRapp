package com.starstudio.loser.phrapp.item.message.contract;

/*
    create by:loser
    date:2018/7/23 22:46
*/

import android.support.v4.app.Fragment;
import android.view.View;

import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.message.presenter.MessageEventListener;

public interface MessageContract {
    interface MessageView extends BaseView<MessageEventListener> {
        View getView();
    }

    interface MessageModel extends BaseModel {

    }

    interface MessagePresenter extends BasePresenter<MessageView, MessageModel> {

    }

}
