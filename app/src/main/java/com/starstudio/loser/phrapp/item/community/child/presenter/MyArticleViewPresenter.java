package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/8/3 21:30
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyArticleViewPresenter extends PHRPresenter<MyArticleContract.MyArticleChildView, MyArticleContract.MyArticleContractModel> implements MyArticleContract.MyArticleChildPresenter {
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

        @Override
        public void startArticleActivity(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("data", mList.get(position).toString());
            Intent intent = new Intent(getActivity(), ArticleActivity.class);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
    };

    public MyArticleViewPresenter(Activity activity) {
        super(activity);
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
