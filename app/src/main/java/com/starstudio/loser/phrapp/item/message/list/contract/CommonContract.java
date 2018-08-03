package com.starstudio.loser.phrapp.item.message.list.contract;

/*
    create by:loser
    date:2018/7/24 11:01
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.presenter.FragmentEventListener;

import java.util.List;

import io.reactivex.Observable;


public interface CommonContract {
    interface View extends BaseView<FragmentEventListener> {
        void loadRecyclerView(List<UsefulData> list);
    }

    interface Model extends BaseModel {
        Observable<BaseBean> fetchModel(PHRFragment fragment);

        void convertToUsefulDataAndLoadView(BaseBean baseBean);
    }

    interface Presenter extends BasePresenter<CommonContract.View, CommonContract.Model> {
        void loadView(List<UsefulData> list);
    }

    interface SimpleModel extends BaseModel {
        Observable<BaseBean> fetchModel(PHRFragment fragment);

        void saveToLeanCloud(BaseBean baseBean);

        void getLeanCloudData();
    }

    interface SimplePresenter extends BasePresenter<CommonContract.View, CommonContract.SimpleModel> {
        void loadView(List<UsefulData> list);
    }


}
