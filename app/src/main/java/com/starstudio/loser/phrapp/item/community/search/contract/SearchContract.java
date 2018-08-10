package com.starstudio.loser.phrapp.item.community.search.contract;

/*
    create by:loser
    date:2018/8/9 13:29
*/

import android.content.Context;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVObject;
import com.starstudio.loser.phrapp.common.base.BaseModel;
import com.starstudio.loser.phrapp.common.base.BasePresenter;
import com.starstudio.loser.phrapp.common.base.BaseView;
import com.starstudio.loser.phrapp.item.community.search.presenter.SearchEventListener;

import java.util.List;

public interface SearchContract {
    interface SearchContractView extends BaseView<SearchEventListener> {
        void showHistory(List<String> history);

        void showResult(List<AVObject> list);

        void showSearch(List<AVObject> list);
    }

    interface SearchContractModel extends BaseModel {
        void find(String string);

        void findAndSave(String string, Context context);

        void loadHistory(Context context);

        void clearHistory(Context context);
    }


    interface SearchContractPresenter extends BasePresenter<SearchContractView, SearchContractModel> {

        void tellToShowResult(List<AVObject> list);

        void tellToShowSearch(List<AVObject> list);

        void tellToShowHistory(List<String> list);
    }


}
