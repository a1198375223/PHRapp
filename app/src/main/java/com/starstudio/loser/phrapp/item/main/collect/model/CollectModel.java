package com.starstudio.loser.phrapp.item.main.collect.model;

/*
    create by:loser
    date:2018/8/8 14:48
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.main.collect.contract.CollectContract;

import java.util.ArrayList;
import java.util.List;

public class CollectModel extends PHRModel implements CollectContract.CollectContractModel {
    private CollectContract.CollectContractPresenter mPresenter;

    public CollectModel(CollectContract.CollectContractPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getCollectData() {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("您没有登入噢!");
        } else {
            AVQuery<AVObject> collect = new AVQuery<>("Collect");
            collect.include("collect_user").include("article").include("article.article_user").whereEqualTo("collect_user", avUser);
            collect.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        List<AVObject> article = new ArrayList<>();
                        for (AVObject object : list) {
                            article.add(object.getAVObject("article"));
                        }
                        mPresenter.toLoadView(article);
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }
}
