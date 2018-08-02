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
import com.starstudio.loser.phrapp.item.community.child.presenter.ChildEventListener;

import java.util.List;

public interface MyArticleContract {
    interface MyArticleContractView extends BaseView<ChildEventListener>{
        void setData(List<AVObject> list);

        void load(List<AVObject> list, boolean hasMore);

        void tellToRefresh();
    }

    interface MyArticleContractModel extends BaseModel {
        void getDataFromLeanCloud();

        void getRefreshData(int size);

        void getDataSkip(int size);
    }

    interface MyArticleContractPresenter extends BasePresenter<MyArticleContractView, MyArticleContractModel> {
        void setViewData(List<AVObject> list);

        void toLoadView(List<AVObject> list);
    }

}
