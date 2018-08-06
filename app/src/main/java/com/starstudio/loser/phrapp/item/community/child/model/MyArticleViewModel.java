package com.starstudio.loser.phrapp.item.community.child.model;

/*
    create by:loser
    date:2018/8/3 21:44
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;

import java.util.ArrayList;
import java.util.List;

public class MyArticleViewModel extends PHRModel implements MyArticleContract.MyArticleContractModel {
    private MyArticleContract.MyArticleChildPresenter mPresenter;

    public MyArticleViewModel(MyArticleContract.MyArticleChildPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getDataFromLeanCloud() {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdBy");
        query.limit(10);
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list.size() != 0) {
                    mPresenter.setViewData(list);
                } else {
                    ToastyUtils.showError("出错啦");
                }
            }
        });
    }
    @Override
    public void getRefreshData(int size) {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdAt");
        if (size < 10) {
            query.limit(10);
        } else {
            query.limit(size);
        }
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null && list.size() != 0) {
                    mPresenter.setViewData(list);
                } else {
                    ToastyUtils.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void getDataSkip(final int size) {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdBy");
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> slice = new ArrayList<>();
                    for (int i=size; i<list.size(); i++) {
                        slice.add(list.get(i));
                    }
                    mPresenter.toLoadView(slice);
                } else {
                    ToastyUtils.showError("出错啦");
                }
            }
        });
    }
}

