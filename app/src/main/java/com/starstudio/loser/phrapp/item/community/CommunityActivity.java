package com.starstudio.loser.phrapp.item.community;

/*
    create by:loser
    date:2018/7/30 17:49
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.fragment.view.WriteFragment;
import com.starstudio.loser.phrapp.item.community.model.CommunityModel;
import com.starstudio.loser.phrapp.item.community.presenter.CommunityPresenter;
import com.starstudio.loser.phrapp.item.community.view.CommunityView;

import java.util.Objects;

public class CommunityActivity extends PHRActivity {
    private CommunityContract.CommunityPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_community);
        Toolbar toolbar = (Toolbar) findViewById(R.id.phr_community_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mPresenter = new CommunityPresenter(this);
        mPresenter.setModel(new CommunityModel());
        mPresenter.setView(new CommunityView(this));
        mPresenter.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
