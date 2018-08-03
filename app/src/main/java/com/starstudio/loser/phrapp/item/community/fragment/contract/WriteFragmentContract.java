package com.starstudio.loser.phrapp.item.community.fragment.contract;

/*
    create by:loser
    date:2018/7/31 14:38
*/

import com.avos.avoscloud.AVUser;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.fragment.presenter.WriteFragmentEventListener;

public interface WriteFragmentContract {
    interface WriteView extends BaseView<WriteFragmentEventListener> {
        void destroySelf();

        void showErrorDialog();
    }

    interface WriteModel extends BaseModel {
        void saveToDataBase(String title, String text, AVUser avUser);
    }

    interface WritePresenter extends BasePresenter<WriteView, WriteModel> {
        void tellViewToDestroy();
    }

}
