package com.starstudio.loser.phrapp.item.community.search;

/*
    create by:loser
    date:2018/8/9 13:24
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.community.search.contract.SearchContract;
import com.starstudio.loser.phrapp.item.community.search.model.SearchModel;
import com.starstudio.loser.phrapp.item.community.search.presenter.SearchPresenter;
import com.starstudio.loser.phrapp.item.community.search.view.SearchActivityView;

public class SearchActivity extends PHRActivity {
    private SearchContract.SearchContractPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_search_layout);

        mPresenter = new SearchPresenter(this);
        mPresenter.setView(new SearchActivityView(this));
        mPresenter.setModel(new SearchModel(mPresenter));
        mPresenter.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
