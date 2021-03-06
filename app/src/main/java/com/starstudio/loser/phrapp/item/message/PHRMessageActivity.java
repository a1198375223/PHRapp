package com.starstudio.loser.phrapp.item.message;

/*
    create by:loser
    date:2018/7/23 22:40
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.message.contract.MessageContract;
import com.starstudio.loser.phrapp.item.message.model.MessageModelmpl;
import com.starstudio.loser.phrapp.item.message.presenter.MessagePresenterImpl;
import com.starstudio.loser.phrapp.item.message.view.MessageViewImpl;

import java.util.Objects;

public class PHRMessageActivity extends PHRActivity {
    private MessageContract.MessagePresenter mPresenter;
    private Toolbar mToolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_message);
        mToolbar = (Toolbar) findViewById(R.id.phr_message_toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
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
