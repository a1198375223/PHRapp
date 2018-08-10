package com.starstudio.loser.phrapp.item.community;

/*
    create by:loser
    date:2018/7/30 17:49
*/

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.community.callback.AFCallback;
import com.starstudio.loser.phrapp.item.community.contract.CommunityContract;
import com.starstudio.loser.phrapp.item.community.model.CommunityModel;
import com.starstudio.loser.phrapp.item.community.presenter.CommunityPresenter;
import com.starstudio.loser.phrapp.item.community.search.SearchActivity;
import com.starstudio.loser.phrapp.item.community.view.CommunityView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.Objects;

public class CommunityActivity extends PHRActivity implements AFCallback{
    private CommunityContract.CommunityPresenter mPresenter;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_community);
        Toolbar toolbar = (Toolbar) findViewById(R.id.phr_community_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
//        EventBus.getDefault().register(this);
        mPresenter = new CommunityPresenter(this);
        mPresenter.setModel(new CommunityModel());
        mPresenter.setView(new CommunityView(this));
        mPresenter.attach();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phr_menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.phr_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void tellToRefresh() {
        mPresenter.childRefresh();
    }
}
