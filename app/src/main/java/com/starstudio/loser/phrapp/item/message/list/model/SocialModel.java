package com.starstudio.loser.phrapp.item.message.list.model;

/*
    create by:loser
    date:2018/7/25 14:06
*/

import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.model.server.SocialServer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocialModel extends PHRModel implements CommonContract.Model {
    private CommonContract.Presenter mPresenter;

    public SocialModel(CommonContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Observable<BaseBean> fetchModel(PHRFragment fragment) {
        return SocialServer.getInstance(fragment.getContext()).fetchInfo()
                .compose(fragment.<BaseBean>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void convertToUsefulDataAndLoadView(BaseBean baseBean) {
        List<UsefulData> list = new ArrayList<>();
        for (BaseBean.ResultBean.DataBean item: baseBean.getResult().getData()) {
            UsefulData usefulData = new UsefulData();
            usefulData.setTitle(item.getTitle());
            usefulData.setImage1(item.getThumbnail_pic_s());
            usefulData.setImage2(item.getThumbnail_pic_s02());
            usefulData.setImage3(item.getThumbnail_pic_s03());
            usefulData.setAuthor(item.getAuthor_name());
            usefulData.setUrl(item.getUrl());
            list.add(usefulData);
        }
        mPresenter.loadView(list);
    }

}