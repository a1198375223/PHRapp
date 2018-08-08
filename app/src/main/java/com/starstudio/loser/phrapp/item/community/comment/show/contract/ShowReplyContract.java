package com.starstudio.loser.phrapp.item.community.comment.show.contract;

/*
    create by:loser
    date:2018/8/7 17:06
*/

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.comment.show.presenter.ShowReplyEventListener;

import java.util.List;

public interface ShowReplyContract {
    interface ShowReplyContractView extends BaseView<ShowReplyEventListener> {
        void setData(List<AVObject> list);
    }

    interface ShowReplyContractModel extends BaseModel {
        void getReplyFromLeanCloud(AVObject avObject);

        void saveReply(String comment, AVObject reply, AVObject article, int id);
    }

    interface ShowReplyContractPresenter extends BasePresenter<ShowReplyContractView, ShowReplyContractModel> {
        void loadView(List<AVObject> list);

        void showError(String error);

        void showSuccess(String success);

        void showWarning(String warning);
    }

}
