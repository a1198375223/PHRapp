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
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.item.community.comment.contract.ArticleContract;

import java.util.ArrayList;
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
    public void getCommentFromLeanCloud(final AVObject avObject) {
        mCurrentAuthor = avObject;
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("reply_to.comment_user");
        query.include("article");
        query.addAscendingOrder("date");
        query.whereEqualTo("article", avObject);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mId = list.size();
                    mPresenter.toLoadView(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void saveComment(String comment) {
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
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
            mCurrentAuthor.increment("reply");
            mCurrentAuthor.setFetchWhenSave(true);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        loadComment();
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }

    }

    @Override
    public void saveReply(String comment, AVObject reply) {
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
            avObject.put("is_author", avUser.equals(mCurrentAuthor.getAVUser("article_user")));
            avObject.put("article", mCurrentAuthor);
            avObject.put("date", new Date(System.currentTimeMillis()));
            reply.increment("reply");
            reply.setFetchWhenSave(true);
            mCurrentAuthor.increment("reply");
            mCurrentAuthor.setFetchWhenSave(true);
            avObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        loadComment();
                    }else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }


    //默认升序
    @Override
    public void loadComment() {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("article");
        query.include("reply_to.comment_user");
        query.addAscendingOrder("date");
        query.whereEqualTo("article", mCurrentAuthor);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToLoadComment(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void loadCommentOnlyAuthor(final AVObject article) {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("article");
        query.addAscendingOrder("date");
        query.whereEqualTo("article", mCurrentAuthor);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> author = new ArrayList<>();
                    for (AVObject av : list) {
                        if (av.getAVUser("comment_user").equals(article.getAVUser("article_user"))) {
                            author.add(av);
                        }
                    }
                    mPresenter.toLoadAuthorComment(author);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void loadCommentByLike() {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("article");
        query.addDescendingOrder("like");
        query.whereEqualTo("article", mCurrentAuthor);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToLoadComment(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void loadCommentById() {
        AVQuery<AVObject> query = new AVQuery<>("Comment");
        query.include("comment_user");
        query.include("reply_to");
        query.include("article");
        query.addDescendingOrder("id");
        query.whereEqualTo("article", mCurrentAuthor);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.tellToLoadComment(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void toCollect(final AVObject article) {
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
        } else {
            AVQuery<AVObject> query = new AVQuery<>("Collect");
            query.include("article").include("collect_user").whereEqualTo("article", article).whereEqualTo("collect_user", avUser);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        if (list.size() == 0) {
                            AVObject collect = new AVObject("Collect");
                            collect.put("article", article);
                            collect.put("collect_user", avUser);
                            collect.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("收藏成功");
                                    } else {
                                        mPresenter.showError("收藏失败");
                                    }
                                }
                            });
                        } else {
                            mPresenter.showWarning("亲,你已经收藏过了!");
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }

    @Override
    public void toLike(final AVObject article) {
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
        } else {
            AVQuery<AVObject> query = new AVQuery<>("Like");
            query.include("article").include("like_user").whereEqualTo("article", article).whereEqualTo("like_user", avUser);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        if (list.size() == 0) {
                            AVObject collect = new AVObject("Like");
                            collect.put("article", article);
                            collect.put("like_user", avUser);
                            article.increment("like");
                            article.setFetchWhenSave(true);
                            collect.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("点赞成功");
                                    } else {
                                        mPresenter.showError("点赞失败");
                                    }
                                }
                            });
                        } else {
                            mPresenter.showWarning("亲,你已经点赞过了!");
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }

    @Override
    public void toDislike(final AVObject article) {
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
        } else {
            AVQuery<AVObject> query = new AVQuery<>("Dislike");
            query.include("article").include("dislike_user").whereEqualTo("article", article).whereEqualTo("dislike_user", avUser);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        if (list.size() == 0) {
                            AVObject collect = new AVObject("Dislike");
                            collect.put("article", article);
                            collect.put("dislike_user", avUser);
                            article.increment("dislike");
                            article.setFetchWhenSave(true);
                            collect.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("点灭成功");
                                    } else {
                                        mPresenter.showError("点灭失败");
                                    }
                                }
                            });
                        } else {
                            mPresenter.showWarning("亲,你已经点灭过了!");
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }

    @Override
    public void toComplaints(final AVObject article) {
        final AVUser avUser = AVUser.getCurrentUser();
        if (avUser == null) {
            mPresenter.showError("请先登入");
        } else {
            AVQuery<AVObject> query = new AVQuery<>("Complaint");
            query.include("article").include("complaints_user").whereEqualTo("article", article).whereEqualTo("complaints_user", avUser);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        if (list.size() == 0) {
                            AVObject collect = new AVObject("Complaint");
                            collect.put("article", article);
                            collect.put("complaints_user", avUser);
                            article.increment("complaints");
                            article.setFetchWhenSave(true);
                            collect.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("举报成功");
                                    } else {
                                        mPresenter.showError("举报失败");
                                    }
                                }
                            });
                        } else {
                            mPresenter.showWarning("亲,你已经举报过了!");
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }


}
