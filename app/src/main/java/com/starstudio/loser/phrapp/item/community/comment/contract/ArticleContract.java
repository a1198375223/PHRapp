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

        void load(List<AVObject> list);

        void showShareDialog(String title, String text);
    }

    interface ArticleContractModel extends BaseModel {
        void getCommentFromLeanCloud(AVObject avObject);

        void saveComment(String comment);

        void saveReply(String comment, AVObject reply);

        void loadComment();

        void loadCommentOnlyAuthor(AVObject article);

        void loadCommentByLike();

        void loadCommentById();

        void toCollect(AVObject article);

        void toLike(AVObject article);

        void toDislike(AVObject article);

        void toComplaints(AVObject article);
    }

    interface ArticleContractPresenter extends BasePresenter<ArticleContractView, ArticleContractModel> {
        void toLoadView(List<AVObject> list);

        void tellToLoadComment(List<AVObject> list);

        void showError(String error);

        void showSuccess(String success);

        void showWarning(String warning);

        void toRefresh();

        void toShare();

        void toCollect();

        void toLike();

        void toDislike();

        void toComplaints();

        void toLoadAuthorComment(List<AVObject> list);

    }

}
