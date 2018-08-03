package com.starstudio.loser.phrapp.item.main.contract;

/*
    create by:loser
    date:2018/7/23 13:28
*/

import android.view.View;

import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;

public interface MainContract {
    interface MainView extends BaseView {
        View getView();
    }

    interface MainModel extends BaseModel {

    }

    interface MainPresenter extends BasePresenter<MainView, MainModel> {

    }
}
