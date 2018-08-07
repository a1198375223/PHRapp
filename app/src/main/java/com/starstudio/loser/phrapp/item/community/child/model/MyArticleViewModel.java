package com.starstudio.loser.phrapp.item.community.child.model;

/*
    create by:loser
    date:2018/8/3 21:44
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;
import com.starstudio.loser.phrapp.item.community.callback.ChildCallBack;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.child.presenter.DynamicViewPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyArticleViewModel extends PHRModel implements MyArticleContract.MyArticleContractModel {
    private MyArticleContract.MyArticleChildPresenter mPresenter;
    private int mSize;


    public MyArticleViewModel(MyArticleContract.MyArticleChildPresenter presenter) {
        this.mPresenter = presenter;

    }

    @Override
    public void getDataFromLeanCloud() {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdAt");
        query.limit(10);
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.setViewData(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }
    @Override
    public void getRefreshData(int size) {
        mSize = size;
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdAt");
        if (size < 10) {
            query.limit(10);
        } else {
            query.limit(size);
        }
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mPresenter.setViewData(list);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void getDataSkip(final int size) {
        mSize = size;
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdBy");
        query.whereEqualTo("article_user", AVUser.getCurrentUser());
        query.include("article_user");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> slice = new ArrayList<>();
                    for (int i=size; i<list.size(); i++) {
                        slice.add(list.get(i));
                    }
                    mPresenter.toLoadView(slice);
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });
    }

    @Override
    public void toDelete(AVObject avObject) {
        final AVObject article = AVObject.createWithoutData("Article", avObject.getObjectId());
        final AVQuery<AVObject> comment = new AVQuery<>("Comment");
        AVQuery<AVObject> like = new AVQuery<>("Like");
        AVQuery<AVObject> dislike = new AVQuery<>("Dislike");
        AVQuery<AVObject> complaint = new AVQuery<>("Complaint");
        AVQuery<AVObject> comment_like = new AVQuery<>("CommentLike");
        AVQuery<AVObject> comment_dislike = new AVQuery<>("CommentDislike");
        AVQuery<AVObject> comment_complaints = new AVQuery<>("CommentComplaints");
        comment_like.include("comment").include("comment_like_user");
        like.include("article").include("like_user").whereEqualTo("article", avObject);
        dislike.include("article").include("dislike_user").whereEqualTo("article", avObject);
        complaint.include("article").include("complaints_user").whereEqualTo("article", avObject);
        comment.include("article");
        comment.whereEqualTo("article", article);
        comment.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject object : list) {
                    AVQuery<AVObject> comment_like = new AVQuery<>("CommentLike");
                    AVQuery<AVObject> comment_dislike = new AVQuery<>("CommentDislike");
                    AVQuery<AVObject> comment_complaints = new AVQuery<>("CommentComplaints");
                    comment_like.include("comment").include("comment_like_user").whereEqualTo("comment", object);
                    comment_dislike.include("comment").include("comment_dislike_user").whereEqualTo("comment", object);
                    comment_complaints.include("comment").include("comment_complaints_user").whereEqualTo("comment", object);
                    comment_like.deleteAllInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                mPresenter.showError("出错啦");
                            }
                        }
                    });
                    comment_complaints.deleteAllInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                mPresenter.showError("出错啦");
                            }
                        }
                    });
                    comment_dislike.deleteAllInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                mPresenter.showError("出错啦");
                            }
                        }
                    });
                }
                comment.deleteAllInBackground(new DeleteCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e != null) {
                            mPresenter.showError("出错啦");
                        }
                    }
                });
            }
        });
        like.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    mPresenter.showError("出错啦");
                }
            }
        });
        dislike.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    mPresenter.showError("出错啦");
                }
            }
        });
        complaint.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    mPresenter.showError("出错啦");
                }
            }
        });
        article.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    getRefreshData(mSize - 1);
                    mPresenter.showSuccess("删除成功");
                } else {
                    mPresenter.showError("出错啦");
                }
            }
        });

    }

    @Override
    public void toShare(AVObject avObject) {

    }

    @Override
    public void toCollect(final AVObject avObject) {
        if (AVUser.getCurrentUser() == null) {
            mPresenter.showError("请先登入");
        } else {
            final AVUser avUser = AVUser.getCurrentUser();
            AVQuery<AVObject> query = new AVQuery<>("Collect");
            query.include("collect_user");
            query.include("article");
            query.whereEqualTo("article", avObject);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null ) {
                        boolean isContain = false;
                        if (list.size() == 0) {
                            isContain = false;
                        } else {
                            for (AVObject av : list) {
                                if (av.getAVUser("collect_user").equals(avUser)) {
                                    isContain = true;
                                    break;
                                }
                            }
                        }
                        if (isContain) {
                            mPresenter.showWarning("亲,您已经收藏过了!");
                        } else {
                            AVObject complaint = new AVObject("Collect");
                            complaint.put("collect_user", avUser);
                            complaint.put("article", avObject);
                            complaint.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("收藏成功");
                                    } else {
                                        mPresenter.showError("收藏失败");
                                    }
                                }
                            });
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }

    @Override
    public void toComplaints(final AVObject avObject) {
        if (AVUser.getCurrentUser() == null) {
            mPresenter.showError("请先登入");
        } else {
            final AVUser avUser = AVUser.getCurrentUser();
            AVQuery<AVObject> query = new AVQuery<>("Complaint");
            query.include("complaints_user");
            query.include("article");
            query.whereEqualTo("article", avObject);
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null ) {
                        boolean isContain = false;
                        if (list.size() == 0) {
                            isContain = false;
                        } else {
                            for (AVObject av : list) {
                                if (av.getAVUser("complaints_user").equals(avUser)) {
                                    isContain = true;
                                    break;
                                }
                            }
                        }
                        if (isContain) {
                            mPresenter.showWarning("亲,您已经举报过了!");
                        } else {
                            AVObject complaint = new AVObject("Complaint");
                            complaint.put("complaints_user", avUser);
                            complaint.put("article", avObject);
                            avObject.increment("complains");
                            avObject.setFetchWhenSave(true);
                            complaint.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        mPresenter.showSuccess("举报成功");
                                    } else {
                                        mPresenter.showError("举报失败");
                                    }
                                }
                            });
                        }
                    } else {
                        mPresenter.showError("出错啦");
                    }
                }
            });
        }
    }
}

