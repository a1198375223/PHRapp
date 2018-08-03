package com.starstudio.loser.phrapp.item.main.presenter;

/*
    create by:loser
    date:2018/7/23 13:28
*/

import android.app.Activity;

import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.main.contract.MainContract;

public class MainPresenterImpl extends PHRPresenter<MainContract.MainView, MainContract.MainModel> implements MainContract.MainPresenter {
    private MainContract.MainView mMainView;
    private MainContract.MainModel mMainModel;


    public MainPresenterImpl(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {
        mMainView = getView();
        mMainModel = getModel();

    }

    @Override
    protected void onDetach() {

    }
}
