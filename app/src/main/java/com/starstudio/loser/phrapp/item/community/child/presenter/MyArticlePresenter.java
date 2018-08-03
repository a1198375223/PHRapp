package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/8/1 20:07
*/

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRFragmentPresenter;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.fragment.view.WriteFragment;

import java.util.ArrayList;
import java.util.List;

public class MyArticlePresenter extends PHRFragmentPresenter<MyArticleContract.MyArticleContractView, MyArticleContract.MyArticleContractModel> implements MyArticleContract.MyArticleContractPresenter {
    private MyArticleContract.MyArticleContractView mView;
    private MyArticleContract.MyArticleContractModel mModel;
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

    public MyArticlePresenter(Fragment fragment) {
        super(fragment);
        mList = new ArrayList<>();
    }


    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);

        mModel.getDataFromLeanCloud();
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
