package com.starstudio.loser.phrapp.item.community.child.contract;

/*
    create by:loser
    date:2018/7/31 20:05
*/

import android.view.View;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.child.presenter.DynamicEventListener;

import java.util.List;

public interface DynamicContract {
    interface DynamicContractModel extends BaseModel {
        void getDataFromLeanCloud();

        void getRefreshData(int size);

        void getDataSkip(int size);

        void toTransfer(AVObject avObject);

        void toComplaints(AVObject avObject);

        void toCollect(AVObject avObject);


    }


    interface DynamicChildPresenter extends BasePresenter<DynamicChildView, DynamicContractModel> {
        void setViewData(List<AVObject> list);

        void toLoadView(List<AVObject> list);

        void showSuccess(String success);

        void showError(String error);

        void showWarning(String warning);
    }


    interface DynamicChildView extends BaseView<DynamicEventListener>{
        View getView();

        void setData(List<AVObject> list);

        void load(List<AVObject> list, boolean hasMore);

        void tellToRefresh();

        void success(String success);

        void error(String error);

        void warning(String warning);

        void showShareDialog(String title, String text);
    }
}
