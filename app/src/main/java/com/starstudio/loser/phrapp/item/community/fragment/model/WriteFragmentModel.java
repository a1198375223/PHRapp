package com.starstudio.loser.phrapp.item.community.fragment.model;

/*
    create by:loser
    date:2018/7/31 15:01
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.community.fragment.contract.WriteFragmentContract;

import java.util.Calendar;
import java.util.Date;

public class WriteFragmentModel extends PHRModel implements WriteFragmentContract.WriteModel {
    private WriteFragmentContract.WritePresenter mPresenter;

    public WriteFragmentModel(WriteFragmentContract.WritePresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void saveToDataBase(String title, String text, AVUser avUser) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = month + "月" + day + "日";
        AVObject article = new AVObject("Article");
        article.put("title", title);
        article.put("text", text);
        article.put("article_user", avUser);
        article.put("date", date);
        article.put("like", 0);
        article.put("dislike", 0);
        article.put("reply", 0);
        article.put("describe", "发布");
        article.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                mPresenter.tellViewToDestroy();
            }
        });
    }
}
