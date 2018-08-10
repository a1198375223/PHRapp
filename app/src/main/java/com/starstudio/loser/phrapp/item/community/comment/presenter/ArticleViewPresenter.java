package com.starstudio.loser.phrapp.item.community.comment.presenter;

/*
    create by:loser
    date:2018/8/5 23:46
*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;
import com.starstudio.loser.phrapp.item.community.comment.show.ShowReplyActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ArticleViewPresenter extends PHRPresenter<ArticleContract.ArticleContractView, ArticleContract.ArticleContractModel> implements ArticleContract.ArticleContractPresenter {
    private ArticleContract.ArticleContractView mView;
    private ArticleContract.ArticleContractModel mModel;
    private AVObject mInitData;
    private List<AVObject> mComment;
    private List<AVObject> mAuthor;

    private ArticleEventListener mListener = new ArticleEventListener() {
        @Override
        public void saveComment(String text, int id) {
            if (id != -1) {
                if (mComment.get(id - 1) == null) {
                    mView.showErrorToast("出错啦");
                    mView.dismissProgressDialog();
                } else {
                    mModel.saveReply(text, mComment.get(id - 1));
                }
            } else {
                mModel.saveComment(text);
            }
        }

        @Override
        public void startShowReplyActivity(int position) {
            if (mComment.get(position) != null) {
                Bundle bundle = new Bundle();
                bundle.putString("comment", mComment.get(position).toString());
                bundle.putString("article", mInitData.toString());
                bundle.putInt("id", mComment.size());
                Intent intent = new Intent((ArticleActivity)getActivity(), ShowReplyActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            } else {
                mView.showErrorToast("出错啦");
            }

        }

        @Override
        public void sortByTime() {
            mModel.loadComment();
        }

        @Override
        public void sortById() {
            mModel.loadCommentById();
        }

        @Override
        public void sortByLike() {
            mModel.loadCommentByLike();
        }

        @Override
        public void sortByAuthor() {
            mModel.loadCommentOnlyAuthor(mInitData);
        }

        @Override
        public void toReplyAuthor(String text, int id) {
            if (mAuthor.get(id - 1) != null) {
                mModel.saveReply(text, mAuthor.get(id - 1));
            } else {
                mView.showErrorToast("出错啦");
                mView.dismissProgressDialog();
            }
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
        mView.showProgressDialog();
        mModel.getCommentFromLeanCloud(mInitData);
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void toLoadView(List<AVObject> list) {
        if (list == null) {
            mComment = new ArrayList<>();
        } else {
            mComment = list;
        }
        mView.setData(mInitData, mComment);
    }

    @Override
    public void tellToLoadComment(List<AVObject> list) {
        if (list == null) {
            mComment = new ArrayList<>();
        } else {
            mComment = list;
        }
        mView.load(mComment);
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
    public void toRefresh() {
        mView.showProgressDialog();
        mModel.loadComment();
    }

    @Override
    public void toShare() {
        mView.showProgressDialog();
        mView.showShareDialog(mInitData.getString("title"), mInitData.getString("text"));
    }

    @Override
    public void toCollect() {
        mView.showProgressDialog();
        mModel.toCollect(mInitData);
    }

    @Override
    public void toLike() {
        mView.showProgressDialog();
        mModel.toLike(mInitData);
    }

    @Override
    public void toDislike() {
        mView.showProgressDialog();
        mModel.toDislike(mInitData);
    }

    @Override
    public void toComplaints() {
        mView.showProgressDialog();
        mModel.toComplaints(mInitData);
    }


    @Override
    public void toLoadAuthorComment(List<AVObject> list) {
        mAuthor = list;
        mView.load(list);
    }


}
