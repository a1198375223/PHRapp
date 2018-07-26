package com.starstudio.loser.phrapp.item.message.list.model;

/*
    create by:loser
    date:2018/7/24 10:09
*/

import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.server.HealthServer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class HealthModel extends PHRModel implements CommonContract.Model {

    @Override
    public Observable<BaseBean> fetchModel(PHRFragment fragment) {
        return HealthServer.getInstance(fragment.getContext()).fetchInfo()
                .compose(fragment.<BaseBean>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
