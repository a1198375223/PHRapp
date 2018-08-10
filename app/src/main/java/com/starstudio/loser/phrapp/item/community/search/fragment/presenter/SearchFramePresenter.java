package com.starstudio.loser.phrapp.item.community.search.fragment.presenter;

/*
    create by:loser
    date:2018/8/10 8:13
*/

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.item.community.search.fragment.contract.SearchFrameContract;


import java.util.ArrayList;
import java.util.List;

public class SearchFramePresenter extends PHRFragmentPresenter<SearchFrameContract.SearchFrameContractView, SearchFrameContract.SearchFrameContractModel> implements SearchFrameContract.SearchFrameContractPresenter{
    private SearchFrameContract.SearchFrameContractView mView;
    private SearchFrameContract.SearchFrameContractModel mModel;
    private SearchFrameEventListener mListener = new SearchFrameEventListener() {
    };

    public SearchFramePresenter(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);

    }

    @Override
    protected void onDetach() {

    }
}
