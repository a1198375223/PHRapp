package com.starstudio.loser.phrapp.item.community.child.model;

/*
    create by:loser
    date:2018/7/31 20:07
*/

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;

import java.util.ArrayList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class DynamicModel extends PHRModel implements DynamicContract.DynamicContractModel {
    private DynamicContract.DynamicContractPresenter mPresenter;

    public DynamicModel(DynamicContract.DynamicContractPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getDataFromLeanCloud() {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdAt");
        query.limit(10);
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
