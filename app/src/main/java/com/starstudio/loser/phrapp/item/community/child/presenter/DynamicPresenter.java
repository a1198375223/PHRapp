package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/7/31 20:08
*/

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;

import java.util.ArrayList;
import java.util.List;

import static com.avos.avoscloud.AVAnalytics.TAG;

public class DynamicPresenter extends PHRFragmentPresenter<DynamicContract.DynamicContractView, DynamicContract.DynamicContractModel> implements DynamicContract.DynamicContractPresenter {
    private DynamicContract.DynamicContractView mView;
    private DynamicContract.DynamicContractModel mModel;
    private List<AVObject> mList;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void toRefresh() {
            mModel.getRefreshData(mList.size());
        }

        @Override
        public void toLoadMore() {
            mModel.getDataSkip(mList.size());
        }
    };

    public DynamicPresenter(Fragment fragment) {
        super(fragment);
        mList = new ArrayList<>();
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();

        mModel.getDataFromLeanCloud();
        mView.setEventListener(mListener);
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void setViewData(List<AVObject> list) {
        mList = list;
        mView.setData(list);
    }

    @Override
    public void toLoadView(List<AVObject> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (list.size() == 0) {
            mView.load(list, false);
        } else {
            mList.addAll(list);
            mView.load(list, true);
        }
    }
}
