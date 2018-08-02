package com.starstudio.loser.phrapp.item.message.list.model;

/*
    create by:loser
    date:2018/7/24 10:09
*/


import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.PHRFragment;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.message.list.contract.CommonContract;
import com.starstudio.loser.phrapp.item.message.list.model.data.BaseBean;
import com.starstudio.loser.phrapp.item.message.list.model.data.UsefulData;
import com.starstudio.loser.phrapp.item.message.list.model.server.HealthServer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class HealthModel extends PHRModel implements CommonContract.SimpleModel {
    private CommonContract.SimplePresenter mPresenter;
    private List<UsefulData> mList;

    public HealthModel(CommonContract.SimplePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Observable<BaseBean> fetchModel(PHRFragment fragment) {
        return HealthServer.getInstance(fragment.getContext()).fetchInfo()
                .compose(fragment.<BaseBean>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveToLeanCloud(BaseBean baseBean) {
        List<BaseBean.ResultBean.DataBean> data = baseBean.getResult().getData();
        checkRepeat(data);
    }

    @Override
    public void getLeanCloudData() {
        if (mList != null && mList.size() != 0) {
            mPresenter.loadView(mList);
        } else {
            AVQuery<AVObject> query = new AVQuery<>("HealthData");
            query.addDescendingOrder("createdAt");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    List<UsefulData> usefulData = setList(list);
                    mPresenter.loadView(usefulData);
                }
            });
        }

    }


    private List<UsefulData> setList(List<AVObject> list) {
        List<UsefulData> lists = new ArrayList<>();
        UsefulData usefulData = null;
        for (AVObject item : list) {
            usefulData = new UsefulData();
            usefulData.setUrl(item.getString("url"));
            usefulData.setTitle(item.getString("title"));
            usefulData.setImage1(item.getString("image1"));
            usefulData.setImage2(item.getString("image2"));
            usefulData.setImage3(item.getString("image3"));
            usefulData.setAuthor(item.getString("author"));
            lists.add(usefulData);
        }
        return lists;
    }

    private void checkRepeat(final List<BaseBean.ResultBean.DataBean> get) {
        mList = new ArrayList<>();
            AVQuery<AVObject> query = new AVQuery<>("HealthData");
            query.limit(90);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    boolean s = hasRepeatItem(list, get);
                    if (!s) {
                        mList.addAll(covertToUsefulData(get));
                    }
                    save(mList);
                }
            });
    }

    private boolean hasRepeatItem(List<AVObject> list, List<BaseBean.ResultBean.DataBean> data) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (int i=0; i<data.size(); i++) {
            for (int j=0; j<list.size(); j++) {
                if (list.get(j).getString("title").equals(data.get(i).getTitle())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void save(List<UsefulData> list){
        for (int i = 0; i< list.size(); i++) {
            AVObject health = new AVObject("HealthData");
            health.put("title", list.get(i).getTitle());
            health.put("image1", list.get(i).getImage1());
            health.put("url", list.get(i).getUrl());
            health.put("author", list.get(i).getAuthor());
            health.put("image2", list.get(i).getImage2());
            health.put("image3", list.get(i).getImage3());
            health.saveInBackground();
        }
    }

    private List<UsefulData> covertToUsefulData(List<BaseBean.ResultBean.DataBean> data){
        List<UsefulData> list = new ArrayList<>();
        for (BaseBean.ResultBean.DataBean item: data) {
            UsefulData usefulData = new UsefulData();
            usefulData.setTitle(item.getTitle());
            usefulData.setImage1(item.getThumbnail_pic_s());
            usefulData.setImage2(item.getThumbnail_pic_s02());
            usefulData.setImage3(item.getThumbnail_pic_s03());
            usefulData.setAuthor(item.getAuthor_name());
            usefulData.setUrl(item.getUrl());
            list.add(usefulData);
        }
        return list;
    }

}
