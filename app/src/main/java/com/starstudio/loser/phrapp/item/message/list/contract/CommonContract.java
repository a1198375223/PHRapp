package com.starstudio.loser.phrapp.item.message.list.contract;

/*
    create by:loser
    date:2018/7/24 11:01
*/

import com.starstudio.loser.phrapp.common.PHRActivity;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.common.base.PHRFragment;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;

import io.reactivex.Observable;


public interface CommonContract {
    interface View extends BaseView {
        void load(BaseBean baseBean);
    }

    interface Model extends BaseModel {
        Observable<BaseBean> fetchModel(PHRFragment fragment);
    }

    interface Presenter extends BasePresenter<CommonContract.View, CommonContract.Model> {

    }
}
