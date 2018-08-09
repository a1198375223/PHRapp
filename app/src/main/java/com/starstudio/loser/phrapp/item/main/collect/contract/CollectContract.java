package com.starstudio.loser.phrapp.item.main.collect.contract;

/*
    create by:loser
    date:2018/8/8 14:48
*/

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.main.collect.presenter.CollectEventListener;

import java.util.List;

public interface CollectContract {
    interface CollectContractView extends BaseView<CollectEventListener> {
        void setData(List<AVObject> list);
    }

    interface CollectContractModel extends BaseModel {
        void getCollectData();
    }

    interface CollectContractPresenter extends BasePresenter<CollectContractView, CollectContractModel> {
        void showError(String error);

        void showSuccess(String success);

        void showWarning(String warning);

        void toLoadView(List<AVObject> list);
    }

}

