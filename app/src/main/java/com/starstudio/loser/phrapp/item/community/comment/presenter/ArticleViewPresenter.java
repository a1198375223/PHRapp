package com.starstudio.loser.phrapp.item.community.comment.presenter;

/*
    create by:loser
    date:2018/8/5 23:46
*/

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ArticleViewPresenter extends PHRPresenter<ArticleContract.ArticleContractView, ArticleContract.ArticleContractModel> implements ArticleContract.ArticleContractPresenter {
    private ArticleContract.ArticleContractView mView;
    private ArticleContract.ArticleContractModel mModel;
    private AVObject mInitData;

    private ArticleEventListener mListener = new ArticleEventListener() {
        @Override
        public void saveComment(String text) {
            mModel.saveComment(text);
        }

        @Override
        public void loadComment() {
            mModel.loadComment();
        }
    };

    public ArticleViewPresenter(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();

        mView.setEventListener(mListener);

        init();
    }

    private void init(){
        Bundle bundle = getActivity().getIntent().getExtras();
        try {
            if (bundle != null) {
                mInitData = AVObject.parseAVObject(bundle.getString("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mModel.getCommentFromLeanCloud(mInitData);
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void toLoadView(List<AVObject> list) {
        mView.setData(mInitData, list);
    }

    @Override
    public void tellToLoadComment(List<AVObject> list) {
        mView.load(list);
    }

    @Override
    public void error() {
        mView.showError();
    }
}
