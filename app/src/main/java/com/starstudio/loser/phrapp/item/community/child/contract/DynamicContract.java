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
import com.starstudio.loser.phrapp.item.community.child.presenter.ChildEventListener;

import java.util.List;

public interface DynamicContract {
    interface DynamicContractModel extends BaseModel {
        void getDataFromLeanCloud();

        void getRefreshData(int size);

        void getDataSkip(int size);
    }

    interface DynamicContractView extends BaseView<ChildEventListener> {

        void setData(List<AVObject> list);

        void load(List<AVObject> list, boolean hasMore);

        void tellToRefresh();
    }

    interface DynamicContractPresenter extends BasePresenter<DynamicContractView, DynamicContractModel> {
        void setViewData(List<AVObject> list);

        void toLoadView(List<AVObject> list);
    }

    interface DynamicChildPresenter extends BasePresenter<DynamicChildView, DynamicContractModel> {
        void setViewData(List<AVObject> list);

        void toLoadView(List<AVObject> list);
    }


    interface DynamicChildView extends DynamicContractView{
        View getView();
    }
}
