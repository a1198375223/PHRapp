package com.starstudio.loser.phrapp.item.message;

/*
    create by:loser
    date:2018/7/23 22:40
*/

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.message.contract.MessageContract;
import com.starstudio.loser.phrapp.item.message.model.MessageModelmpl;
import com.starstudio.loser.phrapp.item.message.presenter.MessagePresenterImpl;
import com.starstudio.loser.phrapp.item.message.view.MessageViewImpl;

public class PHRMessageActivity extends PHRActivity {
    private MessageContract.MessagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_message);
        mPresenter = new MessagePresenterImpl(this);
        mPresenter.setModel(new MessageModelmpl());
        mPresenter.setView(new MessageViewImpl(this));
        mPresenter.attach();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
