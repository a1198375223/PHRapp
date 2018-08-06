package com.starstudio.loser.phrapp.item.community.comment.model;

/*
    create by:loser
    date:2018/8/3 17:27
*/

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;

import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.inflate;

public class ArticleModel extends PHRModel implements ArticleContract.ArticleContractModel {
    private ArticleContract.ArticleContractPresenter mPresenter;
    private int mId;
    private AVObject mCurrentAuthor;

    public ArticleModel(ArticleContract.ArticleContractPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void getCommentFromLeanCloud(AVObject avObject) {
        mCurrentAuthor = avObject;
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.whereEqualTo("article", avObject);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mId = list.size();
                    mPresenter.toLoadView(list);
                }
            }
        });
    }

    @Override
    public void saveComment(String comment) {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.error();
        }else {
            AVObject avObject = new AVObject("Comment");
            avObject.put("comment", comment);
            avObject.put("comment_user", avUser);
            avObject.put("id", ++mId);
            avObject.put("like", 0);
            avObject.put("reply_to", null);
            avObject.put("is_author", avUser.equals(mCurrentAuthor.getAVUser("article_user")));
            avObject.put("article", mCurrentAuthor);
            avObject.put("date", new Date(System.currentTimeMillis()));
            avObject.saveInBackground();
        }

    }

    @Override
    public void loadComment() {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("article");
        query.whereEqualTo("article", mCurrentAuthor);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToLoadComment(list);
                }
            }
        });
    }


}
