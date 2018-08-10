package com.starstudio.loser.phrapp.item.community.search.presenter;

/*
    create by:loser
    date:2018/8/9 13:30
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.community.search.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter extends PHRPresenter<SearchContract.SearchContractView, SearchContract.SearchContractModel> implements SearchContract.SearchContractPresenter {
    private SearchContract.SearchContractView mView;
    private SearchContract.SearchContractModel mModel;
    private List<AVObject> mArticle = new ArrayList<>();

    private SearchEventListener mListener = new SearchEventListener() {
        @Override
        public void toFindAndSave(String history) {
            mModel.findAndSave(history, getActivity());
        }

        @Override
        public void toFind(String search) {
            mModel.find(search);
        }

        @Override
        public void clearHistory() {
            mModel.clearHistory(getActivity());
        }

        @Override
        public void toFindHistory() {
            mModel.loadHistory(getActivity());
        }

        @Override
        public void startArticleActivity(int position) {
            if (mArticle.get(position) != null) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("data", mArticle.get(position).toString());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            } else {
                mView.showErrorToast("出错啦");
            }
        }
    };

    public SearchPresenter(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);
        mModel.loadHistory(getActivity());
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void tellToShowResult(List<AVObject> list) {
        mArticle = list;
        mView.showResult(list);
    }

    @Override
    public void tellToShowSearch(List<AVObject> list) {
        mView.showSearch(list);
    }

    @Override
    public void tellToShowHistory(List<String> list) {
        mView.showHistory(list);
    }
}
