package com.starstudio.loser.phrapp.item.community.comment.show.presenter;

/*
    create by:loser
    date:2018/8/7 17:05
*/

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.PHRPresenter;
import com.starstudio.loser.phrapp.item.community.comment.show.contract.ShowReplyContract;

import java.util.List;


public class ShowReplyPresenter extends PHRPresenter<ShowReplyContract.ShowReplyContractView, ShowReplyContract.ShowReplyContractModel> implements ShowReplyContract.ShowReplyContractPresenter {
    private ShowReplyContract.ShowReplyContractView mView;
    private ShowReplyContract.ShowReplyContractModel mModel;
    private List<AVObject> mReply;
    private AVObject mArticle;
    private int mId;

    private ShowReplyEventListener mListener = new ShowReplyEventListener() {
        @Override
        public void saveComment(String text, int id) {
            if (mReply.get(id) == null) {
                mView.showErrorToast("出错啦");
                mView.dismissProgressDialog();
            } else {
                mModel.saveReply(text, mReply.get(id), mArticle, mId);
            }
        }

    };

    public ShowReplyPresenter(Activity activity) {
        super(activity);
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            try {
                mView.showProgressDialog();
                mModel.getReplyFromLeanCloud(AVObject.parseAVObject(bundle.getString("comment")));
                mArticle = AVObject.parseAVObject(bundle.getString("article"));
                mId = bundle.getInt("id");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mView.showErrorToast("无法加载数据");
        }
    }

    @Override
    protected void onDetach() {

    }

    @Override
    public void loadView(List<AVObject> list) {
        mReply = list;
        mView.setData(list);
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
}
