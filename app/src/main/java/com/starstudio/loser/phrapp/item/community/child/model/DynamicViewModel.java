package com.starstudio.loser.phrapp.item.community.child.model;

/*
    create by:loser
    date:2018/8/3 21:43
*/

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.starstudio.loser.phrapp.common.base.PHRModel;
import com.starstudio.loser.phrapp.common.utils.ToastyUtils;
import com.starstudio.loser.phrapp.item.community.callback.ChildCallBack;
import com.starstudio.loser.phrapp.item.community.child.contract.DynamicContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicViewModel extends PHRModel implements DynamicContract.DynamicContractModel {
    private DynamicContract.DynamicChildPresenter mPresenter;
    private int mSize;

    public DynamicViewModel(DynamicContract.DynamicChildPresenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void getDataFromLeanCloud() {
        AVQuery<AVObject> query = new AVQuery<>("Article");
        query.addDescendingOrder("createdAt");
        query.limit(10);
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
    public void toTransfer(AVObject avObject) {
        if (AVUser.getCurrentUser() == null) {
            mPresenter.showError("请先登入");
        } else {
            AVObject trans = new AVObject("Article");
            trans.put("title", avObject.getString("title"));
            trans.put("text", avObject.getString("text"));
            trans.put("article_user", AVUser.getCurrentUser());
            trans.put("time", new Date(System.currentTimeMillis()));
            trans.put("like", 0);
            trans.put("dislike", 0);
            trans.put("reply", 0);
            trans.put("describe", "转发");
            trans.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        getRefreshData(mSize + 1);
                        mPresenter.showSuccess("转发成功");
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
                    if (e == null) {
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
    public void toShare(AVObject avObject) {

    }
}

