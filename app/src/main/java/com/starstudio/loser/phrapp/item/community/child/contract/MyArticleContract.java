package com.starstudio.loser.phrapp.item.community.child.contract;

/*
    create by:loser
    date:2018/8/1 20:05
*/

import android.view.View;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.child.presenter.MyArticleEventListener;

import java.util.List;

public interface MyArticleContract {

    interface MyArticleContractModel extends BaseModel {
        void getDataFromLeanCloud();

        void getRefreshData(int size);

        void getDataSkip(int size);

        void toDelete(AVObject avObject);


        void toCollect(AVObject avObject);

        void toComplaints(AVObject avObject);

    }


    interface MyArticleChildPresenter extends BasePresenter<MyArticleChildView, MyArticleContractModel> {
        void setViewData(List<AVObject> list);

        void toLoadView(List<AVObject> list);

        void showError(String error);

        void showSuccess(String success);

        void showWarning(String warning);
    }

    interface MyArticleChildView extends BaseView<MyArticleEventListener> {
        View getView();

        void setData(List<AVObject> list);

        void load(List<AVObject> list, boolean hasMore);

        void tellToRefresh();

        void error(String error);

        void success(String success);

        void warning(String warning);

        void showShareDialog(String title, String text);
    }


}
