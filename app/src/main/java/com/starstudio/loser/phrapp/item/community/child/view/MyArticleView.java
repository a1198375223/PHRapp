package com.starstudio.loser.phrapp.item.community.child.view;

/*
    create by:loser
    date:2018/8/1 20:05
*/

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.starstudio.loser.phrapp.R;
import com.starstudio.loser.phrapp.common.base.PHRView;
import com.starstudio.loser.phrapp.common.utils.ShareUtils;
import com.starstudio.loser.phrapp.common.view.PHRShareDialog;
import com.starstudio.loser.phrapp.item.community.child.adapter.MyArticleRvAdapter;
import com.starstudio.loser.phrapp.item.community.child.contract.MyArticleContract;
import com.starstudio.loser.phrapp.item.community.child.presenter.MyArticleEventListener;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class MyArticleView extends PHRView<MyArticleEventListener> implements MyArticleContract.MyArticleChildView {
    private MyArticleRvAdapter mAdapter;
    private SmartRefreshLayout mLayout;
    private View mRootView;
    private PHRShareDialog mShareDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MyArticleView(Activity activity) {
        super(activity);
        mRootView = LayoutInflater.from(activity).inflate(R.layout.phr_my_article_rv_layout, null);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.phr_my_article_recycler_view);
        mLayout = (SmartRefreshLayout) mRootView.findViewById(R.id.phr_my_article_smart_refresh);
        mShareDialog = new PHRShareDialog(getActivity());
        mShareDialog.setListener(new PHRShareDialog.OnShareItemClickListener() {
            @Override
            public void onCancelItemClickListener() {
                if (mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareItemClickListener(int which, String title, String text) {
                switch (which) {
                    case PHRShareDialog.WEIBO:
                        ShareUtils.shareWeibo(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.QQ:
                        ShareUtils.shareQQ(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                    case PHRShareDialog.WECHAT:
                        ShareUtils.shareWechat(title, "http://lc-nujpdri6.cn-n1.lcfile.com/31bc75a685cd225af824.png", text, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                showSuccessToast("分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                showErrorToast("出错啦");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (mShareDialog.isShowing()) {
                            mShareDialog.dismiss();
                        }
                        break;
                }
            }
        });
        mLayout.setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                if (success) {
                    showSuccessToast("刷新成功");
                } else {
                    showErrorToast("刷新失败");
                }
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                if (success) {
                    showSuccessToast("加载成功");
                } else {
                    showErrorToast("加载失败");
                }
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getListener().toLoadMore();
                refreshLayout.finishLoadMore(5000, false, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getListener().toRefresh();
                refreshLayout.finishRefresh(5000, false);
            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

            }
        });

        mAdapter = new MyArticleRvAdapter(activity);
        mAdapter.setListener(new MyArticleRvAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClickListener(int position) {
                getListener().startArticleActivity(position);
            }

            @Override
            public void onMenuItemClickListener(int position, int index) {
                switch (index) {
                    case 0://分享
                        showProgressDialog();
                        getListener().toShare(position);
                        break;
                    case 1://删除
                        showProgressDialog();
                        getListener().toDelete(position);
                        break;
                    case 2://举报
                        showProgressDialog();
                        getListener().toComplaints(position);
                        break;
                    case 3://收藏
                        showProgressDialog();
                        getListener().toCollect(position);
                        break;
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void setData(List<AVObject> list) {
        mAdapter.setList(list);
        mLayout.finishRefresh();
        dismissProgressDialog();
    }

    @Override
    public void load(List<AVObject> list, boolean hasMore) {
        mAdapter.addList(list);
        mLayout.finishLoadMore(0, true, !hasMore);
    }

    @Override
    public void tellToRefresh() {
        getListener().toRefresh();
    }

    @Override
    public void error(String error) {
        showErrorToast(error);
        dismissProgressDialog();
    }

    @Override
    public void success(String success) {
        showSuccessToast(success);
        dismissProgressDialog();
    }

    @Override
    public void warning(String warning) {
        showWarningToast(warning);
        dismissProgressDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void showShareDialog(String title, String text) {
        mShareDialog.setTitle(title);
        mShareDialog.setText(text);
        dismissProgressDialog();
        mShareDialog.showShareDialog();
    }

    @Override
    public View getView() {
        return mRootView;
    }

}
