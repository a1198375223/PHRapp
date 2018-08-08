package com.starstudio.loser.phrapp.item.community.comment.show.model;

/*
    create by:loser
    date:2018/8/7 17:06
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.community.comment.show.contract.ShowReplyContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowReplyModel extends PHRModel implements ShowReplyContract.ShowReplyContractModel {
    private ShowReplyContract.ShowReplyContractPresenter mPresenter;
    private int mId;

    public ShowReplyModel(ShowReplyContract.ShowReplyContractPresenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void getReplyFromLeanCloud(AVObject avObject) {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("reply_to").include("article").include("comment_user").include("reply_to.comment_user").whereEqualTo("reply_to", avObject);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list == null) {
                        mPresenter.loadView(new ArrayList<AVObject>());
                    }else{
                        mPresenter.loadView(list);
                    }
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void saveReply(String comment, AVObject reply, AVObject article, int id) {
        mId = id;
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
        }else {
            AVObject avObject = new AVObject("Comment");
            avObject.put("comment", comment);
            avObject.put("comment_user", avUser);
            avObject.put("id", ++mId);
            avObject.put("like", 0);
            avObject.put("reply_to", reply);
            avObject.put("is_author", avUser.equals(article.getAVUser("article_user")));
            avObject.put("article", article);
            avObject.put("date", new Date(System.currentTimeMillis()));
            reply.increment("reply");
            reply.setFetchWhenSave(true);
            article.increment("reply");
            article.setFetchWhenSave(true);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        mPresenter.showSuccess("回复成功");
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }
}
