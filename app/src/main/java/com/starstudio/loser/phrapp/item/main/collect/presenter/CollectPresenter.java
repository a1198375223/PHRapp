package com.starstudio.loser.phrapp.item.main.collect.presenter;

/*
    create by:loser
    date:2018/8/8 14:48
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.main.collect.contract.CollectContract;

import java.util.ArrayList;
import java.util.List;

public class CollectPresenter extends PHRPresenter<CollectContract.CollectContractView, CollectContract.CollectContractModel> implements CollectContract.CollectContractPresenter {
    private CollectContract.CollectContractView mView;
    private CollectContract.CollectContractModel mModel;
    private List<AVObject> mArticle;
    private CollectEventListener mListener = new CollectEventListener() {
        @Override
        public void startArticleActivity(int position) {
            if (mArticle.get(position) != null) {
                Bundle bundle = new Bundle();
                bundle.putString("data", mArticle.get(position).toString());
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            } else {
                mView.showErrorToast("出错啦");
                mView.dismissProgressDialog();
            }
        }
    };


    public CollectPresenter(Activity activity) {
        super(activity);
        mArticle = new ArrayList<>();
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);

        mView.showProgressDialog();
        mModel.getCollectData();
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void showError(String error) {
        mView.showErrorToast(error);
        mView.dismissProgressDialog();
    }

    @Override
    public void showSuccess(String success) {
        mView.showSuccessToast(success);
        mView.dismissProgressDialog();
    }

    @Override
    public void showWarning(String warning) {
        mView.showWarningToast(warning);
        mView.dismissProgressDialog();
    }

    @Override
    public void toLoadView(List<AVObject> list) {
        mArticle.clear();
        mArticle = list;
        mView.setData(list);
    }
}
