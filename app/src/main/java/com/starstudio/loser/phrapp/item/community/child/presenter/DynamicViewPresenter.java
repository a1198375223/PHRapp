package com.starstudio.loser.phrapp.item.community.child.presenter;

/*
    create by:loser
    date:2018/8/3 21:29
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
import com.starstudio.loser.phrapp.common.view.PHRShareDialog;
import com.starstudio.loser.phrapp.item.community.callback.ChildCallBack;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;
import com.starstudio.loser.phrapp.item.community.comment.ArticleActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DynamicViewPresenter extends PHRPresenter<DynamicContract.DynamicChildView, DynamicContract.DynamicContractModel> implements DynamicContract.DynamicChildPresenter {
    private DynamicContract.DynamicChildView mView;
    private DynamicContract.DynamicContractModel mModel;
    private List<AVObject> mList;


    private DynamicEventListener mListener = new DynamicEventListener() {
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

        @Override
        public void toTransfer(int position) {
            if (mList.get(position) != null) {
                mModel.toTransfer(mList.get(position));
            } else {
                mView.error("出错啦");
            }
        }

        @Override
        public void toShare(int position) {
            if (mList.get(position) != null) {
//                mModel.toShare(mList.get(position));
                mView.showShareDialog(mList.get(position).getString("title"), mList.get(position).getString("text"));
            } else {
                mView.error("出错啦");
            }
        }

        @Override
        public void toComplaints(int position) {
            if (mList.get(position) != null) {
                mModel.toComplaints(mList.get(position));
            } else {
                mView.error("出错啦");
            }
        }

        @Override
        public void toCollect(int position) {
            if (mList.get(position) != null) {
                mModel.toCollect(mList.get(position));
            } else {
                mView.error("出错啦");
            }
        }
    };

    public DynamicViewPresenter(Activity activity) {
        super(activity);
        mList = new ArrayList<>();
    }

    @Override
    protected void onAttach() {
        mView = getView();
        mModel = getModel();
        mView.setEventListener(mListener);

        mView.showProgressDialog();
        mModel.getDataFromLeanCloud();
    }

    @Override
    protected void onDetach() {
        if (mList != null) {
            mList.clear();
            mList = null;
        }
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

    @Override
    public void showSuccess(String success) {
        mView.success(success);
    }

    @Override
    public void showError(String error) {
        mView.error(error);
    }

    @Override
    public void showWarning(String warning) {
        mView.warning(warning);
    }
}
