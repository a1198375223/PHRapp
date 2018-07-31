package com.starstudio.loser.phrapp.item.community.contract;

/*
    create by:loser
    date:2018/7/30 15:45
*/

import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.presenter.CommunityEventListener;

public interface CommunityContract {
    interface CommunityView extends BaseView<CommunityEventListener> {

    }

    interface CommunityModel extends BaseModel {

    }

    interface CommunityPresenter extends BasePresenter<CommunityView, CommunityModel> {


    }
}
