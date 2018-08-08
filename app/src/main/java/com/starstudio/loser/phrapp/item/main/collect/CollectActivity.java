package com.starstudio.loser.phrapp.item.main.collect;

/*
    create by:loser
    date:2018/8/8 14:47
*/

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.item.main.collect.contract.CollectContract;
import com.starstudio.loser.phrapp.item.main.collect.model.CollectModel;
import com.starstudio.loser.phrapp.item.main.collect.presenter.CollectPresenter;
import com.starstudio.loser.phrapp.item.main.collect.view.CollectView;

public class CollectActivity extends PHRActivity {
    private CollectContract.CollectContractPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phr_activity_collect_layout);

        mPresenter = new CollectPresenter(this);
        mPresenter.setView(new CollectView(this));
        mPresenter.setModel(new CollectModel(mPresenter));
        mPresenter.attach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
