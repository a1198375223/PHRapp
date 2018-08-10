package com.starstudio.loser.phrapp.item.community.search.fragment.contract;

/*
    create by:loser
    date:2018/8/10 8:14
*/

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.search.fragment.presenter.SearchFrameEventListener;

import java.util.List;

public interface SearchFrameContract {
    interface SearchFrameContractView extends BaseView<SearchFrameEventListener> {
        void setData(List<AVObject> list);

        void destroySelf();
    }

    interface SearchFrameContractModel extends BaseModel {
    }

    interface SearchFrameContractPresenter extends BasePresenter<SearchFrameContractView, SearchFrameContractModel> {
    }

}
