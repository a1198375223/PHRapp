package com.starstudio.loser.phrapp.item.community.comment.contract;

/*
    create by:loser
    date:2018/8/3 17:27
*/

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.comment.presenter.ArticleEventListener;

import java.util.List;

public interface ArticleContract {
    interface ArticleContractView extends BaseView<ArticleEventListener> {
        void setData(AVObject data, List<AVObject> comment);

        void showError();

        void load(List<AVObject> list);
    }

    interface ArticleContractModel extends BaseModel {
        void getCommentFromLeanCloud(AVObject avObject);

        void saveComment(String comment);

        void loadComment();
    }

    interface ArticleContractPresenter extends BasePresenter<ArticleContractView, ArticleContractModel> {
        void toLoadView(List<AVObject> list);

        void tellToLoadComment(List<AVObject> list);

        void error();
    }

}
